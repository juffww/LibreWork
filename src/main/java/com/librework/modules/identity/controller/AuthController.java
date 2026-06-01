package com.librework.modules.identity.controller;

import com.librework.common.response.ApiResponse;
import com.librework.modules.identity.dto.request.IntrospectRequest;
import com.librework.modules.identity.dto.request.LoginRequest;
import com.librework.modules.identity.dto.request.UserCreationRequest;
import com.librework.modules.identity.dto.response.AuthResponse;
import com.librework.modules.identity.dto.response.IntrospectResponse;
import com.librework.modules.identity.dto.response.UserResponse;
import com.librework.modules.identity.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Identity", description = "Authentication Management")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(
            summary = "Register",
            description = ""
    )
    public ApiResponse<UserResponse> register(@Valid @RequestBody UserCreationRequest request) {
            return ApiResponse.ok(authService.register(request));
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
    public ApiResponse<AuthResponse> login(@Valid @RequestBody LoginRequest request,
                                           HttpServletResponse response)
    {
        AuthResponse authResponse = authService.login(request);
        setRefreshCookie(response, authResponse.getRefreshToken());
        return ApiResponse.ok(authResponse);
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh access token", description = "Rotate refresh token and return a new access token")
    public ApiResponse<AuthResponse> refresh(
            @CookieValue(name = "refreshToken", required = false) String refreshToken,
            HttpServletResponse response) {
        AuthResponse authResponse = authService.refresh(refreshToken);
        setRefreshCookie(response, authResponse.getRefreshToken());
        return ApiResponse.ok(authResponse);
    }

    @PostMapping("/introspect")
    @Operation(summary = "Introspect token", description = "Check if a token is valid or expired")
    public ApiResponse<IntrospectResponse> introspect (@RequestBody IntrospectRequest request)
    {
        return ApiResponse.ok(authService.introspect(request));
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout", description = "Logout user and invalidate token")
    public ApiResponse<?> logout(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @CookieValue(name = "refreshToken", required = false) String refreshToken,
            HttpServletResponse response)
    {
        authService.logout(extractBearerToken(authorization), refreshToken);
        clearRefreshCookie(response);
        return ApiResponse.ok(null);
    }

    private void setRefreshCookie(HttpServletResponse response, String refreshToken) {
        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/api/v1/auth")
                .maxAge(7 * 24 * 60 * 60)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    private void clearRefreshCookie(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/api/v1/auth")
                .maxAge(0)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    private String extractBearerToken(String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return null;
        }
        return authorization.substring(7);
    }
}
