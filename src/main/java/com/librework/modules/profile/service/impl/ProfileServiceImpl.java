package com.librework.modules.profile.service.impl;

import com.librework.modules.profile.service.ProfileService;



import com.librework.common.enums.ProfileType;
import com.librework.exception.AppException;
import com.librework.exception.ErrorCode;
import com.librework.modules.identity.service.CurrentUserService;
import com.librework.modules.profile.dto.response.UserProfileSummary;
import com.librework.modules.identity.service.ActiveProfileService;
import com.librework.modules.identity.service.TokenProviderService;
import com.librework.modules.profile.entity.ClientProfile;
import com.librework.modules.profile.entity.FreelancerProfile;
import com.librework.modules.profile.repository.ClientProfileRepository;
import com.librework.modules.profile.repository.FreelancerProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ClientProfileRepository clientProfileRepository;
    private final FreelancerProfileRepository freelancerProfileRepository;
    private final ActiveProfileService activeProfileService;
    private final CurrentUserService currentUserService;
    private final TokenProviderService tokenProviderService;

    @Transactional
    public UserProfileSummary switchAccount(ProfileType targetType) {
        UUID userId = currentUserService.getCurrentUserId();

        if (targetType == ProfileType.CLIENT) {
            clientProfileRepository.findByUserId(userId)
                    .orElseThrow(() -> new AppException(ErrorCode.SETTING_NOT_FOUND));
        } else {
            freelancerProfileRepository.findByUserId(userId)
                    .orElseThrow(() -> new AppException(ErrorCode.SETTING_NOT_FOUND));
        }

        activeProfileService.setActiveProfile(userId, targetType);
        UserProfileSummary summary = getMe();
        summary.setAccessToken(tokenProviderService.generateToken(
                currentUserService.getCurrentUserName(),
                userId,
                targetType
        ));
        return summary;
    }

    public UserProfileSummary getMe() {
        UUID userId = currentUserService.getCurrentUserId();
        ProfileType activeType = activeProfileService.getActiveProfileType(userId);

        FreelancerProfile freelancer = freelancerProfileRepository.findByUserId(userId).orElse(null);
        ClientProfile client = clientProfileRepository.findByUserId(userId).orElse(null);

        return UserProfileSummary.builder()
                .userId(userId)
                .activeProfileType(activeType)
                .freelancer(freelancer == null ? null :
                        UserProfileSummary.FreelancerInfo.builder()
                        .profileId(freelancer.getId())
                        .displayName(freelancer.getTitle())
                        .build())
                .client(client == null ? null :
                        UserProfileSummary.ClientInfo.builder()
                        .profileId(client.getId())
                        .displayName(client.getCompanyName())
                        .build())
                .build();
    }
}
