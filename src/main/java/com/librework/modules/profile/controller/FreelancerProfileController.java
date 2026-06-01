package com.librework.modules.profile.controller;

import com.librework.common.response.ApiResponse;
import com.librework.modules.profile.dto.response.FreelancerProfileResponse;
import com.librework.modules.profile.service.FreelancerProfileService;
import com.librework.modules.profile.dto.request.FreelancerProfileUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/freelancer")
@RequiredArgsConstructor
@Tag(name = "Freelancer Profile", description = "Freelancer Profile Management")
public class FreelancerProfileController {

    private final FreelancerProfileService freelancerProfileService;

    @GetMapping("/profile")
    @Operation(summary = "Get Freelancer profile", description = "Get profile info of the current Freelancer")
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<FreelancerProfileResponse> getProfile() {
        return ApiResponse.ok(freelancerProfileService.getProfile());
    }

    @PostMapping("/avatar")
    @Operation(summary = "Upload avatar/cover", description = "Upload professional avatar image")
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<String> uploadAvatar(@RequestParam("file") MultipartFile file) throws IOException {
        return ApiResponse.ok(freelancerProfileService.uploadAvatar(file));
    }

    @PutMapping("/profile")
    @Operation(summary = "Update profile", description = "Update descriptions, professional skills of the Freelancer")
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<FreelancerProfileResponse> updateFreelancerProfile(@RequestBody FreelancerProfileUpdateRequest request) {
        return ApiResponse.ok(freelancerProfileService.updateProfile(request));
    }
}
