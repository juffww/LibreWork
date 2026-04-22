package com.librework.modules.profile.application.service.impl;

import com.librework.common.exception.AppException;
import com.librework.common.exception.ErrorCode;
import com.librework.infrastructure.security.SecurityUtils;
import com.librework.infrastructure.storage.CloudinaryService;
import com.librework.modules.profile.application.dto.request.FreelancerProfileUpdateRequest;
import com.librework.modules.profile.application.dto.response.UserProfileSummary;
import com.librework.modules.profile.domain.entity.FreelancerProfile;
import com.librework.modules.profile.application.mapper.ProfileMapper;
import com.librework.modules.profile.domain.repository.ClientProfileRepository;
import com.librework.modules.profile.domain.repository.FreelancerProfileRepository;
import com.librework.modules.profile.application.service.ProfileQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileQueryServiceImpl implements ProfileQueryService {

    private final FreelancerProfileRepository freelancerProfileRepository;
    private final ClientProfileRepository clientProfileRepository;
    private final ProfileMapper profileMapper;
    private final CloudinaryService cloudinaryService;

    @Override
    @Transactional(readOnly = true)
    public UserProfileSummary getUserProfiles(UUID userId) {
        FreelancerProfile freelancerProfile = freelancerProfileRepository.findByUserId(userId).orElse(null);
        UUID freelancerProfileId = freelancerProfile != null ? freelancerProfile.getId() : null;
        String freelancerAvatarUrl = freelancerProfile != null ? freelancerProfile.getAvatarUrl() : null;

        var clientProfiles = clientProfileRepository.findAllByUserId(userId)
                .stream()
                .map(cp -> UserProfileSummary.ClientProfileInfo.builder()
                        .id(cp.getId())
                        .displayName(cp.getCompanyName())
                        .avatarUrl(cp.getLogoUrl())
                        .build())
                .collect(Collectors.toList());

        return UserProfileSummary.builder()
                .freelancerProfileId(freelancerProfileId)
                .freelancerAvaterUrl(freelancerAvatarUrl)
                .clientProfiles(clientProfiles)
                .build();
    }

    @Override
    public String uploadFreelancerAvatar(MultipartFile file)
    {
        UUID userId = UUID.fromString(SecurityUtils.getCurrentJwt().getClaim("userId"));
        if(file == null || file.isEmpty())
        {
            throw new AppException(ErrorCode.FILE_NOT_VALID);
        }

        String freelancerAvatarUrl = cloudinaryService.uploadImage(file);

        FreelancerProfile freelancerProfile =   freelancerProfileRepository.findByUserId(userId)
                .orElseThrow(null);

        freelancerProfile.setAvatarUrl(freelancerAvatarUrl);

        freelancerProfileRepository.save(freelancerProfile);
        return freelancerAvatarUrl;
    }

    @Override
    public FreelancerProfile updateFreelancerProfile(FreelancerProfileUpdateRequest request)
    {
        UUID userId = UUID.fromString(SecurityUtils.getCurrentJwt().getClaim("userId"));
        FreelancerProfile fProfile = freelancerProfileRepository.findByUserId(userId).orElse(null);
        profileMapper.updateFreelancerProfile(fProfile, request);

        assert fProfile != null;
        return freelancerProfileRepository.save(fProfile);
    }
}
