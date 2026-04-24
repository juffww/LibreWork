package com.librework.modules.profile.application.service.impl;

import com.librework.common.enums.ProfileType;
import com.librework.common.exception.AppException;
import com.librework.common.exception.ErrorCode;
import com.librework.common.port.CurrentUserPort;
import com.librework.modules.profile.application.dto.response.UserProfileSummary;
import com.librework.modules.profile.application.port.in.ProfileUseCase;
import com.librework.common.port.ActiveProfilePort;
import com.librework.modules.profile.domain.entity.ClientProfile;
import com.librework.modules.profile.domain.entity.FreelancerProfile;
import com.librework.modules.profile.domain.repository.ClientProfileRepository;
import com.librework.modules.profile.domain.repository.FreelancerProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileUseCase {

    private final ClientProfileRepository clientProfileRepository;
    private final FreelancerProfileRepository freelancerProfileRepository;
    private final ActiveProfilePort activeProfilePort;
    private final CurrentUserPort currentUserPort;

    @Override
    @Transactional
    public UserProfileSummary switchAccount(ProfileType targetType) {
        UUID userId = currentUserPort.getCurrentUserId();

        if (targetType == ProfileType.CLIENT) {
            clientProfileRepository.findByUserId(userId)
                    .orElseThrow(() -> new AppException(ErrorCode.SETTING_NOT_FOUND));
        } else {
            freelancerProfileRepository.findByUserId(userId)
                    .orElseThrow(() -> new AppException(ErrorCode.SETTING_NOT_FOUND));
        }

        activeProfilePort.setActiveProfile(userId, targetType);
        return getMe();
    }

    @Override
    public UserProfileSummary getMe() {
        UUID userId = currentUserPort.getCurrentUserId();
        ProfileType activeType = activeProfilePort.getActiveProfileType(userId);

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