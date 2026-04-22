package com.librework.modules.identity.presentation.controller;

import com.librework.common.response.ApiResponse;
import com.librework.modules.identity.application.dto.request.UserSettingUpdateRequest;
import com.librework.modules.identity.application.dto.response.UserSettingResponse;
import com.librework.modules.identity.application.service.UserSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/settings")
@RequiredArgsConstructor
public class UserSettingController {
    private final UserSettingService userSettingService;

    @PatchMapping("/me")
    public ApiResponse<UserSettingResponse> updateSetting(@RequestBody UserSettingUpdateRequest request)
    {
        return ApiResponse.ok(userSettingService.updateSetting(request));
    }

    @GetMapping
    public ApiResponse<UserSettingResponse> getUserSetting()
    {
        return ApiResponse.ok(userSettingService.getUserSetting());
    }
}
