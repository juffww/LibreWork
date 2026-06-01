package com.librework.modules.identity.service;

import com.librework.modules.identity.dto.request.IntrospectRequest;
import com.librework.modules.identity.dto.request.LoginRequest;
import com.librework.modules.identity.dto.request.UserCreationRequest;
import com.librework.modules.identity.dto.response.AuthResponse;
import com.librework.modules.identity.dto.response.IntrospectResponse;
import com.librework.modules.identity.dto.response.UserResponse;

public interface AuthService {
    UserResponse register(UserCreationRequest request);

    AuthResponse login(LoginRequest request);

    AuthResponse refresh(String refreshToken);

    IntrospectResponse introspect(IntrospectRequest request);

    void logout(String accessToken, String refreshToken);
}
