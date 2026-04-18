package com.librework.modules.identity.service;

import com.librework.modules.identity.dto.request.IntrospectRequest;
import com.librework.modules.identity.dto.request.LoginRequest;
import com.librework.modules.identity.dto.request.LogoutRequest;
import com.librework.modules.identity.dto.request.UserCreationRequest;
import com.librework.modules.identity.dto.response.AuthResponse;
import com.librework.modules.identity.dto.response.IntrospectResponse;

import java.util.UUID;

public interface AuthService {
    UUID register(UserCreationRequest request);
    AuthResponse login(LoginRequest request);
    IntrospectResponse introspect (IntrospectRequest request);
    void logout(LogoutRequest request);
}
