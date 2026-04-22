package com.librework.modules.profile.application.service;

import com.librework.modules.profile.application.dto.request.FreelancerProfileUpdateRequest;
import com.librework.modules.profile.application.dto.response.UserProfileSummary;
import com.librework.modules.profile.domain.entity.FreelancerProfile;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface ProfileQueryService {
    UserProfileSummary getUserProfiles(UUID userId);
    FreelancerProfile updateFreelancerProfile(FreelancerProfileUpdateRequest request);
    String uploadFreelancerAvatar(MultipartFile file);

}
