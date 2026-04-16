package com.librework.modules.profiles.controller;

import com.librework.common.response.ApiResponse;
import com.librework.modules.profiles.dto.request.SwitchAccountRequest;
import com.librework.modules.profiles.dto.request.UserSettingUpdateRequest;
import com.librework.modules.profiles.dto.response.ProfileResponse;
import com.librework.modules.profiles.entity.UserSetting;
import com.librework.modules.profiles.service.UserSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/settings")
@RequiredArgsConstructor
public class UserSettingController {
    private final UserSettingService userSettingService;

    @PatchMapping("/me")
    public ApiResponse<UserSetting> updateSetting(@RequestBody UserSettingUpdateRequest request)
    {
        return ApiResponse.ok(userSettingService.updateSetting(request));
    }

    @PostMapping("/client-profile")
    public ApiResponse<ProfileResponse> createClientProfile(@RequestBody com.librework.modules.profiles.dto.request.ClientProfileCreationRequest request) {
        return ApiResponse.ok(userSettingService.createClientProfile(request));
    }

    @GetMapping
    public ApiResponse<UserSetting> getUserSetting()
    {
        return ApiResponse.ok(userSettingService.getUserSetting());
    }

    @PostMapping("/switch-account")
    public ApiResponse<ProfileResponse> switchAccount(@RequestBody SwitchAccountRequest request) {
        return ApiResponse.ok(userSettingService.switchActiveContext(request.getProfileType(), request.getTargetClientProfileId()));
    }
}
