package com.librework.modules.identity.service.impl;

import com.librework.common.event.UserRegisteredEvent;
import com.librework.common.exception.AppException;
import com.librework.common.exception.ErrorCode;
import com.librework.modules.identity.dto.request.IntrospectRequest;
import com.librework.modules.identity.dto.request.LoginRequest;
import com.librework.modules.identity.dto.request.LogoutRequest;
import com.librework.modules.identity.dto.request.UserCreationRequest;
import com.librework.modules.identity.dto.response.AuthResponse;
import com.librework.modules.identity.dto.response.IntrospectResponse;
import com.librework.modules.identity.entity.User;
import com.librework.modules.identity.infrastructure.security.RedisTokenBlacklistService;
import com.librework.modules.identity.repository.UserRepository;
import com.librework.modules.identity.infrastructure.security.JwtService;
import com.librework.modules.identity.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;
    private final RedisTokenBlacklistService redisTokenBlacklistService;

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
                .status(User.UserStatus.ACTIVE)
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        User savedUser = userRepository.save(user);

        eventPublisher.publishEvent(new UserRegisteredEvent(savedUser.getId(), savedUser.getEmail(), request.getAccountType()));

        return savedUser.getId();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        //- Authentication sẽ gọi UserDetailService để load user và kiểm tra password
        //- Sau đó so sánh password qua encoder password đã cấu hình trong securityConfig
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        String token = jwtService.generateToken(user.getEmail(), user.getId());

        return AuthResponse.builder()
                .accessToken(token)
                .userId(user.getId())
                .email(user.getEmail())
                .build();
    }

    @Override
    public IntrospectResponse introspect(IntrospectRequest request)
    {
        boolean valid = jwtService.isTokenValid(request.getToken());
        return IntrospectResponse.builder()
                .valid(valid)
                .build();
    }

    @Override
    public void logout(LogoutRequest request)
    {
        try {
            String token = request.getToken();
            String jti = jwtService.getJwtId(token);
            Date expirationTime = jwtService.getExpirationTime(token);
            long remainingTime = expirationTime.getTime() - System.currentTimeMillis();

            if (remainingTime > 0) {
                redisTokenBlacklistService.addToBlacklist(jti, remainingTime);
            }
        } catch (Exception e) {
            log.error("Lỗi khi xử lý logout token", e);
        }
    }
}
