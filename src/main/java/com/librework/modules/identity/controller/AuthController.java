package com.librework.modules.identity.controller;

import com.librework.common.response.ApiResponse;
import com.librework.modules.identity.dto.request.UserCreationRequest;
import com.librework.modules.identity.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<?> register(@Valid @RequestBody UserCreationRequest request) {
            return ApiResponse.ok(authService.register(request));
    }
}
