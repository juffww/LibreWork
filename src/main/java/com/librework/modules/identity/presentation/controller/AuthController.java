package com.librework.modules.identity.presentation.controller;

import com.librework.common.response.ApiResponse;
import com.librework.modules.identity.application.dto.request.IntrospectRequest;
import com.librework.modules.identity.application.dto.request.LoginRequest;
import com.librework.modules.identity.application.dto.request.LogoutRequest;
import com.librework.modules.identity.application.dto.request.UserCreationRequest;
import com.librework.modules.identity.application.dto.response.AuthResponse;
import com.librework.modules.identity.application.dto.response.IntrospectResponse;
import com.librework.modules.identity.application.port.in.AuthUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Identity", description = "Authentication Management")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUseCase authUseCase;

    @PostMapping("/register")
    @Operation(
            summary = "Register",
            description = ""
    )
    public ApiResponse<?> register(@Valid @RequestBody UserCreationRequest request) {
            return ApiResponse.ok(authUseCase.register(request));
    }

    @PostMapping("/login")
    @Operation(
            summary = "Login",
            description = "Return JWT access token and refresh token"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Login successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "Wrong login data",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(
                                    example = "{\n  \"success\": false,\n  \"message\": \"Incorrect username or password\",\n  \"data\": null\n}"
                            )
                    )
            )
    })
    public ApiResponse<AuthResponse> login(@Valid @RequestBody LoginRequest request)
    {
        return ApiResponse.ok(authUseCase.login(request));
    }

    @PostMapping("/introspect")
    @Operation(summary = "Introspect token", description = "Check if a token is valid or expired")
    public ApiResponse<IntrospectResponse> introspect (@RequestBody IntrospectRequest request)
    {
        return ApiResponse.ok(authUseCase.introspect(request));
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout", description = "Logout user and invalidate token")
    public ApiResponse<?> logout(@RequestBody LogoutRequest request)
    {
        authUseCase.logout(request);
        return ApiResponse.ok(null);
    }
}
