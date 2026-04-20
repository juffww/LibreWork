package com.librework.modules.profiles.controller;

import com.librework.common.response.ApiResponse;
import com.librework.modules.profiles.dto.request.SwitchAccountRequest;
import com.librework.modules.profiles.dto.response.ProfileResponse;
import com.librework.modules.profiles.service.UserSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/me")
@RequiredArgsConstructor
public class AccountController {

    private final UserSettingService userSettingService;

    @PostMapping("/switch-profile")
    public ApiResponse<ProfileResponse<?>> switchAccount(
            @RequestBody SwitchAccountRequest request
    ) {
        return ApiResponse.ok(
                userSettingService.switchAccount(
                        request.getTargetType(),
                        request.getTargetClientProfileId()
                )
        );
    }

    @GetMapping("")
    public ApiResponse<?> getProfile()
    {
        return ApiResponse.ok(userSettingService.getCurrentProfile());
    }
}
