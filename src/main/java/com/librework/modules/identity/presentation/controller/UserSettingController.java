package com.librework.modules.identity.presentation.controller;

import com.librework.common.response.ApiResponse;
import com.librework.modules.identity.application.dto.request.UserSettingUpdateRequest;
import com.librework.modules.identity.application.dto.response.UserSettingResponse;
import com.librework.modules.identity.application.port.in.UserSettingUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/settings")
@RequiredArgsConstructor
@Tag(name = "User Settings", description = "APIs for managing user settings")
public class UserSettingController {
    private final UserSettingUseCase userSettingUseCase;

    @PatchMapping("/me")
    @Operation(summary = "Update user settings", description = "Update system/personal settings of the current user")
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<UserSettingResponse> updateSetting(@RequestBody UserSettingUpdateRequest request)
    {
        return ApiResponse.ok(userSettingUseCase.updateSetting(request));
    }

    @GetMapping("/me")
    @Operation(summary = "Get user settings", description = "Get settings data of the logged-in user")
    @SecurityRequirement(name = "bearerAuth") // endpoint need JWT
    public ApiResponse<UserSettingResponse> getUserSetting()
    {
        return ApiResponse.ok(userSettingUseCase.getUserSetting());
    }
}
