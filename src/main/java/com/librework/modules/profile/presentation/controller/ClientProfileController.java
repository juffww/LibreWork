package com.librework.modules.profile.presentation.controller;
import com.librework.common.response.ApiResponse;
import com.librework.modules.profile.application.dto.request.ClientProfileCreationRequest;
import com.librework.modules.profile.application.dto.response.ClientProfileResponse;
import com.librework.modules.profile.application.port.in.ClientProfileUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/v1/client-profiles")
@RequiredArgsConstructor
@Tag(name = "Client Profile", description = "Client Profile Management")
public class ClientProfileController {

    private final ClientProfileUseCase clientProfileUseCase;

    @PostMapping
    @Operation(summary = "Create Client Profile", description = "Create a client profile for a user")
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<ClientProfileResponse> createClientProfile(@RequestBody ClientProfileCreationRequest request) {
        return ApiResponse.ok(clientProfileUseCase.createClientProfile(request));
    }
}
