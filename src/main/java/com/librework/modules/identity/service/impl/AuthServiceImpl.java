package com.librework.modules.identity.service.impl;

import com.librework.common.exception.AppException;
import com.librework.common.exception.ErrorCode;
import com.librework.modules.identity.dto.request.UserCreationRequest;
import com.librework.modules.identity.entity.User;
import com.librework.modules.identity.entity.UserProfile;
import com.librework.modules.identity.repository.UserRepository;
import com.librework.modules.identity.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UUID register(UserCreationRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USERNAME_EXISTED);
        }

        User user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .fullName(request.getFullName())
                .password(request.getPassword())
                .build();

        UserProfile profile = UserProfile.builder()
                .user(user)
                .build();

        user.setProfile(profile);

        User savedUser = userRepository.save(user);

        return savedUser.getId();
    }
}
