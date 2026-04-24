package com.librework.modules.profile.application.service.impl;

import com.librework.common.exception.AppException;
import com.librework.common.exception.ErrorCode;
import com.librework.common.port.CurrentUserPort;
import com.librework.modules.profile.application.dto.request.FreelancerProfileUpdateRequest;
import com.librework.modules.profile.application.dto.response.FreelancerProfileResponse;
import com.librework.modules.profile.application.mapper.FreelancerProfileMapper;
import com.librework.modules.profile.application.port.in.FreelancerProfileUseCase;
import com.librework.modules.profile.application.port.out.AvatarStoragePort;
import com.librework.modules.profile.domain.entity.FreelancerProfile;
import com.librework.modules.profile.domain.repository.FreelancerProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FreelancerProfileServiceImpl implements FreelancerProfileUseCase {

    private final FreelancerProfileRepository freelancerProfileRepository;
    private final FreelancerProfileMapper profileMapper;
    private final CurrentUserPort currentUserPort;
    private final AvatarStoragePort avatarStoragePort;

    @Override
    @Transactional(readOnly = true)
    public FreelancerProfileResponse getProfile() {
        UUID userId = currentUserPort.getCurrentUserId();
        FreelancerProfile profile = freelancerProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return profileMapper.toFreelancerProfileResponse(profile);
    }

    @Override
    @Transactional
    public FreelancerProfileResponse updateProfile(FreelancerProfileUpdateRequest request) {
        UUID userId = currentUserPort.getCurrentUserId();
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

    @Override
    @Transactional
    public String uploadAvatar(MultipartFile file) {
        UUID userId = currentUserPort.getCurrentUserId();
        if (file == null || file.isEmpty()) {
            throw new AppException(ErrorCode.FILE_NOT_VALID);
        }
        FreelancerProfile profile = freelancerProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        String avatarUrl = avatarStoragePort.upload(file);
        profile.changeAvatar(avatarUrl);
        freelancerProfileRepository.save(profile);
        return avatarUrl;
    }
}