package com.librework.modules.profile.application.port.in;

import com.librework.modules.profile.application.dto.request.FreelancerProfileUpdateRequest;
import com.librework.modules.profile.application.dto.response.FreelancerProfileResponse;
import org.springframework.web.multipart.MultipartFile;

// application/port/in/FreelancerProfileUseCase.java
public interface FreelancerProfileUseCase {
    FreelancerProfileResponse getProfile();
    FreelancerProfileResponse updateProfile(FreelancerProfileUpdateRequest request);
    String uploadAvatar(MultipartFile file);
}