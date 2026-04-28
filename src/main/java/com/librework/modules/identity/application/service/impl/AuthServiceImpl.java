package com.librework.modules.identity.application.service.impl;

import com.librework.modules.identity.domain.event.UserRegisteredEvent;
import com.librework.common.exception.AppException;
import com.librework.common.exception.ErrorCode;
import com.librework.modules.identity.application.dto.request.IntrospectRequest;
import com.librework.modules.identity.application.dto.request.LoginRequest;
import com.librework.modules.identity.application.dto.request.LogoutRequest;
import com.librework.modules.identity.application.dto.request.UserCreationRequest;
import com.librework.modules.identity.application.dto.response.AuthResponse;
import com.librework.modules.identity.application.dto.response.IntrospectResponse;
import com.librework.modules.identity.application.port.out.TokenBlacklistPort;
import com.librework.modules.identity.application.port.out.TokenProviderPort;
import com.librework.modules.identity.application.port.in.AuthUseCase;
import com.librework.modules.identity.domain.entity.User;
import com.librework.common.enums.UserStatus;
import com.librework.modules.identity.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthUseCase {

    // Dependency vào Repository (Domain)
    private final UserRepository userRepository;

    // Dependency vào Spring Security
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;

    // TÍNH CHẤT CLEAN ARCHITECTURE: CHỈ DEPEND VÀO PORT (INTERFACE)
    private final TokenProviderPort tokenProviderPort;
    private final TokenBlacklistPort tokenBlacklistPort;

    @Override
    @Transactional
    public UUID register(UserCreationRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        if (userRepository.existsByUsername(request.getUserName())) {
            throw new AppException(ErrorCode.USERNAME_EXISTED);
        }

        User user = User.builder()
                .email(request.getEmail())
                .username(request.getUserName())
                .fullName(request.getFullName())
                .status(UserStatus.ACTIVE)
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        User savedUser = userRepository.save(user);

        // Phát sự kiện để các module khác (ví dụ: Profile) tự lắng nghe và khởi tạo dữ liệu
        eventPublisher.publishEvent(new UserRegisteredEvent(savedUser.getId(), savedUser.getEmail(), request.getAccountType()));

        return savedUser.getId();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        // 1. Xác thực qua Spring Security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // 2. Lấy thông tin User từ cơ sở dữ liệu
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // 3. Sinh token thông qua Port (Không quan tâm bên dưới dùng JWT hay gì khác)
        String token = tokenProviderPort.generateToken(user.getUsername(), user.getId());

        // 4. Trả về thông tin định danh thuần túy
        return AuthResponse.builder()
                .accessToken(token)
                .userId(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .status(user.getStatus())
                .build();
    }

    @Override
    public IntrospectResponse introspect(IntrospectRequest request) {
        // Kiểm tra tính hợp lệ của token qua Port
        boolean valid = tokenProviderPort.isTokenValid(request.getToken());
        return IntrospectResponse.builder()
                .valid(valid)
                .build();
    }

    @Override
    public void logout(LogoutRequest request) {
        try {
            String token = request.getToken();

            // Lấy JTI và thời gian hết hạn qua Port
            String jti = tokenProviderPort.getJwtId(token);
            Date expirationTime = tokenProviderPort.getExpirationTime(token);
            long remainingTime = expirationTime.getTime() - System.currentTimeMillis();

            // Lưu vào Blacklist qua Port (Không cần biết là lưu vào Redis hay DB)
            if (remainingTime > 0) {
                tokenBlacklistPort.addToBlacklist(jti, remainingTime);
            }
        } catch (Exception e) {
            log.error("Lỗi khi xử lý logout token", e);
        }
    }
}