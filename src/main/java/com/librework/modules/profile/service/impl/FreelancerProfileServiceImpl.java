package com.librework.modules.profile.service.impl;

import com.librework.modules.profile.service.FreelancerProfileService;



import com.librework.exception.AppException;
import com.librework.exception.ErrorCode;
import com.librework.common.enums.ProfileType;
import com.librework.modules.identity.service.ActiveProfileService;
import com.librework.modules.identity.service.CurrentUserService;
import com.librework.infrastructure.storage.CloudinaryService;
import com.librework.modules.profile.dto.request.FreelancerProfileUpdateRequest;
import com.librework.modules.profile.dto.response.FreelancerProfileResponse;
import com.librework.modules.profile.mapper.FreelancerProfileMapper;
import com.librework.modules.profile.entity.FreelancerProfile;
import com.librework.modules.profile.repository.FreelancerProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FreelancerProfileServiceImpl implements FreelancerProfileService {

    private final FreelancerProfileRepository freelancerProfileRepository;
    private final FreelancerProfileMapper profileMapper;
    private final CurrentUserService currentUserService;
    private final ActiveProfileService activeProfileService;
    private final CloudinaryService cloudinaryService;

    @Transactional(readOnly = true)
    public FreelancerProfileResponse getProfile() {
        UUID userId = currentUserService.getCurrentUserId();
        requireFreelancerProfile(userId);
        FreelancerProfile profile = freelancerProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return profileMapper.toFreelancerProfileResponse(profile);
    }

    @Transactional
    public FreelancerProfileResponse updateProfile(FreelancerProfileUpdateRequest request) {
        UUID userId = currentUserService.getCurrentUserId();
        requireFreelancerProfile(userId);
        FreelancerProfile profile = freelancerProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        profile.updateProfileInfo(
                request.getTitle(),
                request.getOverview(),
                request.getHourlyRate(),
                request.getExperienceLevel(),
                request.getAvailability()
        );
        FreelancerProfile saved = freelancerProfileRepository.save(profile);
        return profileMapper.toFreelancerProfileResponse(saved);
    }

    @Transactional
    public String uploadAvatar(MultipartFile file) {
        UUID userId = currentUserService.getCurrentUserId();
        requireFreelancerProfile(userId);
        if (file == null || file.isEmpty()) {
            throw new AppException(ErrorCode.FILE_NOT_VALID);
        }
        FreelancerProfile profile = freelancerProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        String avatarUrl = cloudinaryService.uploadImage(file);
        profile.changeAvatar(avatarUrl);
        freelancerProfileRepository.save(profile);
        return avatarUrl;
    }

    private void requireFreelancerProfile(UUID userId) {
        if (activeProfileService.getActiveProfileType(userId) != ProfileType.FREELANCER) {
            throw new AppException(ErrorCode.FORBIDDEN);
        }
    }
}
