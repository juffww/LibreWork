package com.librework.modules.profiles.service;

import com.librework.modules.profiles.dto.request.FreelancerProfileUpdateRequest;
import com.librework.modules.profiles.dto.response.UserProfileSummary;
import com.librework.modules.profiles.entity.FreelancerProfile;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface ProfileQueryService {
    UserProfileSummary getUserProfiles(UUID userId);
    FreelancerProfile updateFreelancerProfile(FreelancerProfileUpdateRequest request);
    String uploadFreelancerAvatar(MultipartFile file);

}
