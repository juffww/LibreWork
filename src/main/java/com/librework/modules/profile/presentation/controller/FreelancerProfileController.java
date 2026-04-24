package com.librework.modules.profile.presentation.controller;

import com.librework.common.response.ApiResponse;
import com.librework.modules.profile.application.dto.response.FreelancerProfileResponse;
import com.librework.modules.profile.application.port.in.FreelancerProfileUseCase;
import com.librework.modules.profile.application.dto.request.FreelancerProfileUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/freelancer")
@RequiredArgsConstructor
public class FreelancerProfileController {

    private final FreelancerProfileUseCase freelancerProfileUseCase;

    @GetMapping("/profile")
    public ApiResponse<FreelancerProfileResponse> getProfile() {
        return ApiResponse.ok(freelancerProfileUseCase.getProfile());
    }
    @PostMapping("/avatar")
    public ApiResponse<String> uploadAvatar(@RequestParam("file") MultipartFile file) throws IOException {
        return ApiResponse.ok(freelancerProfileUseCase.uploadAvatar(file));
    }

    @PutMapping("/profile")
    public ApiResponse<FreelancerProfileResponse> updateFreelancerProfile(@RequestBody FreelancerProfileUpdateRequest request) {
        return ApiResponse.ok(freelancerProfileUseCase.updateProfile(request));
    }
}
