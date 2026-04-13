package com.librework.modules.identity.service;

import com.librework.modules.identity.dto.request.UserCreationRequest;

import java.util.UUID;

public interface AuthService {
    UUID register(UserCreationRequest request);
}
