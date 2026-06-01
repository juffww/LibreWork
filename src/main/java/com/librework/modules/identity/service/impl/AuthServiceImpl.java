package com.librework.modules.identity.service.impl;

import com.librework.modules.identity.service.AuthService;
import com.librework.modules.identity.service.UserSettingService;



import com.librework.exception.AppException;
import com.librework.exception.ErrorCode;
import com.librework.modules.identity.dto.request.IntrospectRequest;
import com.librework.modules.identity.dto.request.LoginRequest;
import com.librework.modules.identity.dto.request.UserCreationRequest;
import com.librework.modules.identity.dto.response.AuthResponse;
import com.librework.modules.identity.dto.response.IntrospectResponse;
import com.librework.modules.identity.dto.response.UserResponse;
import com.librework.modules.identity.service.TokenBlacklistService;
import com.librework.modules.identity.service.RefreshTokenStoreService;
import com.librework.modules.identity.service.TokenProviderService;
import com.librework.modules.identity.entity.User;
import com.librework.common.enums.UserStatus;
import com.librework.modules.identity.service.ActiveProfileService;
import com.librework.modules.identity.repository.UserRepository;
import com.librework.modules.profile.service.impl.ProfileInitializationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;
import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final ProfileInitializationService profileInitializationService;
    private final UserSettingService userSettingService;

    private final TokenProviderService tokenProviderService;
    private final TokenBlacklistService tokenBlacklistService;
    private final RefreshTokenStoreService refreshTokenStoreService;
    private final ActiveProfileService activeProfileService;

    @Transactional
    public UserResponse register(UserCreationRequest request) {
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

        profileInitializationService.initializeForNewUser(savedUser.getId(), request.getAccountType());
        userSettingService.initDefaultSetting(savedUser.getId(), request.getAccountType());

        return UserResponse.builder()
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .username(savedUser.getUsername())
                .fullName(savedUser.getFullName())
                .status(savedUser.getStatus())
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        // 1. Xác thực qua Spring Security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // 2. Lấy thông tin User từ cơ sở dữ liệu
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        String token = tokenProviderService.generateToken(
                user.getUsername(),
                user.getId(),
                activeProfileService.getActiveProfileType(user.getId())
        );
        String refreshToken = tokenProviderService.generateRefreshToken(user.getUsername(), user.getId());
        rememberRefreshToken(user.getId(), refreshToken);

        return AuthResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .status(user.getStatus())
                .build();
    }

    public AuthResponse refresh(String refreshToken) {
        if (refreshToken == null || refreshToken.isBlank() || !tokenProviderService.isRefreshTokenValid(refreshToken)) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        UUID userId = tokenProviderService.getUserId(refreshToken);
        String jti = tokenProviderService.getJwtId(refreshToken);
        if (!refreshTokenStoreService.isCurrent(userId, jti)) {
            refreshTokenStoreService.revoke(userId);
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        if (user.getStatus() != UserStatus.ACTIVE) {
            refreshTokenStoreService.revoke(userId);
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        blacklistIfStillAlive(refreshToken);
        String accessToken = tokenProviderService.generateToken(
                user.getUsername(),
                user.getId(),
                activeProfileService.getActiveProfileType(user.getId())
        );
        String newRefreshToken = tokenProviderService.generateRefreshToken(user.getUsername(), user.getId());
        rememberRefreshToken(user.getId(), newRefreshToken);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(newRefreshToken)
                .userId(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .status(user.getStatus())
                .build();
    }

    public IntrospectResponse introspect(IntrospectRequest request) {
        // Kiểm tra tính hợp lệ của token qua Port
        boolean valid = tokenProviderService.isTokenValid(request.getToken());
        return IntrospectResponse.builder()
                .valid(valid)
                .build();
    }

    public void logout(String accessToken, String refreshToken) {
        blacklistIfStillAlive(accessToken);
        if (refreshToken != null && !refreshToken.isBlank()) {
            try {
                UUID userId = tokenProviderService.getUserId(refreshToken);
                refreshTokenStoreService.revoke(userId);
            } catch (Exception e) {
                log.debug("Cannot resolve refresh token owner during logout");
            }
            blacklistIfStillAlive(refreshToken);
        }
    }

    private void rememberRefreshToken(UUID userId, String refreshToken) {
        String refreshJti = tokenProviderService.getJwtId(refreshToken);
        Date expiration = tokenProviderService.getExpirationTime(refreshToken);
        long ttlMillis = expiration.getTime() - System.currentTimeMillis();
        if (ttlMillis > 0) {
            refreshTokenStoreService.store(userId, refreshJti, Duration.ofMillis(ttlMillis));
        }
    }

    private void blacklistIfStillAlive(String token) {
        if (token == null || token.isBlank()) return;
        try {
            String jti = tokenProviderService.getJwtId(token);
            Date expirationTime = tokenProviderService.getExpirationTime(token);
            long remainingTime = expirationTime.getTime() - System.currentTimeMillis();
            if (remainingTime > 0) {
                tokenBlacklistService.addToBlacklist(jti, remainingTime);
            }
        } catch (Exception e) {
            log.debug("Cannot blacklist token: {}", e.getMessage());
        }
    }
}
