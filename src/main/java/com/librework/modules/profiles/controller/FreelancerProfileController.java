package com.librework.modules.profiles.controller;

import com.librework.common.response.ApiResponse;
import com.librework.common.security.SecurityUtils;
import com.librework.common.service.CloudinaryService;
import com.librework.modules.profiles.entity.FreelancerProfile;
import com.librework.modules.profiles.repository.FreelancerProfileRepository;
import com.librework.modules.profiles.dto.request.FreelancerProfileUpdateRequest;
import com.librework.modules.profiles.dto.response.UserProfileSummary;
import com.librework.modules.profiles.service.ProfileQueryService;
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
