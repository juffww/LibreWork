package com.librework.modules.profile.presentation.controller;

import com.librework.common.response.ApiResponse;
import com.librework.infrastructure.security.SecurityUtils;
import com.librework.infrastructure.storage.CloudinaryService;
import com.librework.modules.profile.domain.entity.FreelancerProfile;
import com.librework.modules.profile.application.dto.request.FreelancerProfileUpdateRequest;
import com.librework.modules.profile.application.dto.response.UserProfileSummary;
import com.librework.modules.profile.application.service.ProfileQueryService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/me")
@RequiredArgsConstructor
public class FreelancerProfileController {
    private final ProfileQueryService profileQueryService;

    @PostMapping("/freelancer-avater")
    public ApiResponse<String> uploadAvatar(@RequestParam("file") MultipartFile file) throws IOException {
        return ApiResponse.ok(profileQueryService.uploadFreelancerAvatar(file));
    }

    @PutMapping()
    public ApiResponse<FreelancerProfile> updateFreelancerProfile(@RequestBody FreelancerProfileUpdateRequest request) {
        FreelancerProfile updatedProfile = profileQueryService.updateFreelancerProfile(request);
        return ApiResponse.ok(updatedProfile);
    }
}
