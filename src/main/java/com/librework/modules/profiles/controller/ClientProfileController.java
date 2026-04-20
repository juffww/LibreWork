package com.librework.modules.profiles.controller;

import com.librework.common.response.ApiResponse;
import com.librework.modules.profiles.dto.request.ClientProfileCreationRequest;
import com.librework.modules.profiles.dto.response.ProfileResponse;
import com.librework.modules.profiles.entity.ClientProfile;
import com.librework.modules.profiles.service.UserSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/client-profiles")
@RequiredArgsConstructor
public class ClientProfileController {

    private final UserSettingService userSettingService;

    @PostMapping
    public ApiResponse<ProfileResponse<ClientProfile>> createClientProfile(
            @RequestBody ClientProfileCreationRequest request
    ) {
        return ApiResponse.ok(userSettingService.createClientProfile(request));
    }
}
