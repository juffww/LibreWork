package com.librework.modules.identity.application.service;

import com.librework.modules.identity.application.dto.request.IntrospectRequest;
import com.librework.modules.identity.application.dto.request.LoginRequest;
import com.librework.modules.identity.application.dto.request.LogoutRequest;
import com.librework.modules.identity.application.dto.request.UserCreationRequest;
import com.librework.modules.identity.application.dto.response.AuthResponse;
import com.librework.modules.identity.application.dto.response.IntrospectResponse;

import java.util.UUID;

public interface AuthService {
    UUID register(UserCreationRequest request);
    AuthResponse login(LoginRequest request);
    IntrospectResponse introspect (IntrospectRequest request);
    void logout(LogoutRequest request);
}
