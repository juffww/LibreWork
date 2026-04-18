package com.librework.modules.identity.controller;

import com.librework.common.response.ApiResponse;
import com.librework.modules.identity.dto.request.IntrospectRequest;
import com.librework.modules.identity.dto.request.LoginRequest;
import com.librework.modules.identity.dto.request.LogoutRequest;
import com.librework.modules.identity.dto.request.UserCreationRequest;
import com.librework.modules.identity.dto.response.AuthResponse;
import com.librework.modules.identity.dto.response.IntrospectResponse;
import com.librework.modules.identity.service.AuthService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

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
