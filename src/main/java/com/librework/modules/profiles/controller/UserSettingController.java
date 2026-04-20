package com.librework.modules.profiles.controller;

import com.librework.common.response.ApiResponse;
import com.librework.modules.profiles.dto.request.SwitchAccountRequest;
import com.librework.modules.profiles.dto.request.UserSettingUpdateRequest;
import com.librework.modules.profiles.dto.response.ProfileResponse;
import com.librework.modules.profiles.entity.ClientProfile;
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

    @GetMapping
    public ApiResponse<UserSetting> getUserSetting()
    {
        return ApiResponse.ok(userSettingService.getUserSetting());
    }
}
