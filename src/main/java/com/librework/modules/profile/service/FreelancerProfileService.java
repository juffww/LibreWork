package com.librework.modules.profile.service;

import com.librework.modules.profile.dto.request.FreelancerProfileUpdateRequest;
import com.librework.modules.profile.dto.response.FreelancerProfileResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FreelancerProfileService {
    FreelancerProfileResponse getProfile();

    FreelancerProfileResponse updateProfile(FreelancerProfileUpdateRequest request);

    String uploadAvatar(MultipartFile file);
}
