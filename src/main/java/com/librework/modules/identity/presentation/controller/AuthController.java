package com.librework.modules.identity.presentation.controller;

import com.librework.common.response.ApiResponse;
import com.librework.modules.identity.application.dto.request.IntrospectRequest;
import com.librework.modules.identity.application.dto.request.LoginRequest;
import com.librework.modules.identity.application.dto.request.LogoutRequest;
import com.librework.modules.identity.application.dto.request.UserCreationRequest;
import com.librework.modules.identity.application.dto.response.AuthResponse;
import com.librework.modules.identity.application.dto.response.IntrospectResponse;
import com.librework.modules.identity.application.port.in.AuthUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUseCase authUseCase;

    @PostMapping("/register")
    public ApiResponse<?> register(@Valid @RequestBody UserCreationRequest request) {
            return ApiResponse.ok(authUseCase.register(request));
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@Valid @RequestBody LoginRequest request)
    {
        return ApiResponse.ok(authUseCase.login(request));
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> introspect (@RequestBody IntrospectRequest request)
    {
        return ApiResponse.ok(authUseCase.introspect(request));
    }

    @PostMapping("/logout")
    public ApiResponse<?> logout(@RequestBody LogoutRequest request)
    {
        authUseCase.logout(request);
        return ApiResponse.ok(null);
    }
}
