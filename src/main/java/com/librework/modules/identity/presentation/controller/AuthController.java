package com.librework.modules.identity.presentation.controller;

import com.librework.common.response.ApiResponse;
import com.librework.infrastructure.storage.CloudinaryService;
import com.librework.modules.identity.application.dto.request.IntrospectRequest;
import com.librework.modules.identity.application.dto.request.LoginRequest;
import com.librework.modules.identity.application.dto.request.LogoutRequest;
import com.librework.modules.identity.application.dto.request.UserCreationRequest;
import com.librework.modules.identity.application.dto.response.AuthResponse;
import com.librework.modules.identity.application.dto.response.IntrospectResponse;
import com.librework.modules.identity.application.service.AuthService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final CloudinaryService cloudinaryService;
    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<?> register(@Valid @RequestBody UserCreationRequest request) {
            return ApiResponse.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@Valid @RequestBody LoginRequest request)
    {
        return ApiResponse.ok(authService.login(request));
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> introspect (@RequestBody IntrospectRequest request)
    {
        return ApiResponse.ok(authService.introspect(request));
    }

    @PostMapping("/logout")
    public ApiResponse<?> logout(@RequestBody LogoutRequest request)
    {
        authService.logout(request);
        return ApiResponse.ok(null);
    }
}
