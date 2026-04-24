package com.librework.modules.identity.presentation.controller;

import com.librework.common.response.ApiResponse;
import com.librework.modules.identity.application.dto.request.UserSettingUpdateRequest;
import com.librework.modules.identity.application.dto.response.UserSettingResponse;
import com.librework.modules.identity.application.port.in.UserSettingUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/settings")
@RequiredArgsConstructor
public class UserSettingController {
    private final UserSettingUseCase userSettingUseCase;

    @PatchMapping("/me")
    public ApiResponse<UserSettingResponse> updateSetting(@RequestBody UserSettingUpdateRequest request)
    {
        return ApiResponse.ok(userSettingUseCase.updateSetting(request));
    }

    @GetMapping("/me")
    public ApiResponse<UserSettingResponse> getUserSetting()
    {
        return ApiResponse.ok(userSettingUseCase.getUserSetting());
    }
}
