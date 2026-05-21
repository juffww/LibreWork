package com.librework.modules.profile.presentation.controller;
import com.librework.common.response.ApiResponse;
import com.librework.modules.profile.application.dto.request.SwitchAccountRequest;
import com.librework.modules.profile.application.dto.response.UserProfileSummary;
import com.librework.modules.profile.application.port.in.ProfileUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/me")
@RequiredArgsConstructor
@Tag(name = "Profile", description = "Personal Profile Management (switch account, view profile)")
public class ProfileController {

    private final ProfileUseCase profileUseCase;

    @PostMapping("/switch-profile")
    @Operation(summary = "Switch current profile type", description = "Switch Account Context between Freelancer and Client")
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<UserProfileSummary> switchAccount(@Valid @RequestBody SwitchAccountRequest request) {
        return ApiResponse.ok(profileUseCase.switchAccount(request.getTargetType()));
    }

    @GetMapping
    @Operation(summary = "Get current user profile (Me)", description = "Get status and current account type")
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<?> getMe()
    {
        return ApiResponse.ok(profileUseCase.getMe());
    }
}
