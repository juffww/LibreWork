package com.librework.modules.profile.presentation.controller;
import com.librework.common.response.ApiResponse;
import com.librework.modules.profile.application.dto.request.ClientProfileCreationRequest;
import com.librework.modules.profile.application.dto.response.ClientProfileResponse;
import com.librework.modules.profile.application.port.in.ClientProfileUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/v1/client-profiles")
@RequiredArgsConstructor
public class ClientProfileController {

    private final ClientProfileUseCase clientProfileUseCase;

    @PostMapping
    public ApiResponse<ClientProfileResponse> createClientProfile(@RequestBody ClientProfileCreationRequest request) {
        return ApiResponse.ok(clientProfileUseCase.createClientProfile(request));
    }
}
