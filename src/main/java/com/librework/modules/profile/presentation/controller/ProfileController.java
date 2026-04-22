package com.librework.modules.profile.presentation.controller;
import com.librework.common.response.ApiResponse;
import com.librework.modules.profile.application.dto.request.SwitchAccountRequest;
import com.librework.modules.profile.application.dto.response.ProfileResponse;
import com.librework.modules.profile.application.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/me")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;
    @PostMapping("/switch-profile")
    public ApiResponse<ProfileResponse<?>> switchAccount(
            @RequestBody SwitchAccountRequest request
    ) {
        return ApiResponse.ok(
                profileService.switchAccount(
                        request.getTargetType(),
                        request.getTargetClientProfileId()
                )
        );
    }
    @GetMapping("")
    public ApiResponse<?> getProfile()
    {
        return ApiResponse.ok(profileService.getCurrentProfile());
    }
}
