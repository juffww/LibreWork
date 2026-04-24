package com.librework.modules.profile.presentation.controller;
import com.librework.common.response.ApiResponse;
import com.librework.modules.profile.application.dto.request.SwitchAccountRequest;
import com.librework.modules.profile.application.dto.response.UserProfileSummary;
import com.librework.modules.profile.application.port.in.ProfileUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/me")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileUseCase profileUseCase;

    @PostMapping("/switch-profile")
    public ApiResponse<UserProfileSummary> switchAccount(@Valid @RequestBody SwitchAccountRequest request) {
        return ApiResponse.ok(profileUseCase.switchAccount(request.getTargetType()));
    }

    @GetMapping
    public ApiResponse<?> getMe()
    {
        return ApiResponse.ok(profileUseCase.getMe());
    }
}
