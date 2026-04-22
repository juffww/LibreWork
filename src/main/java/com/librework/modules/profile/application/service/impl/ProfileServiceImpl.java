package com.librework.modules.profile.application.service.impl;

import com.librework.common.enums.ProfileType;
import com.librework.common.exception.AppException;
import com.librework.common.exception.ErrorCode;
import com.librework.infrastructure.security.SecurityUtils;
import com.librework.modules.profile.application.dto.request.ClientProfileCreationRequest;
import com.librework.modules.profile.application.dto.response.ClientProfileResponse;
import com.librework.modules.profile.application.dto.response.FreelancerProfileResponse;
import com.librework.modules.profile.application.dto.response.ProfileResponse;
import com.librework.modules.profile.domain.entity.ClientProfile;
import com.librework.modules.profile.domain.entity.FreelancerProfile;
import com.librework.modules.profile.application.mapper.ProfileMapper;
import com.librework.modules.profile.domain.repository.ClientProfileRepository;
import com.librework.modules.profile.domain.repository.FreelancerProfileRepository;
import com.librework.modules.profile.application.service.ProfileService;
import com.librework.modules.profile.domain.port.ActiveProfileProvider;
import com.librework.common.event.ActiveProfileChangedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final ClientProfileRepository clientProfileRepository;
    private final FreelancerProfileRepository freelancerProfileRepository;
    private final ProfileMapper profileMapper;
    private final ActiveProfileProvider activeProfileProvider;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public ProfileResponse<ClientProfile> createClientProfile(ClientProfileCreationRequest request)
    {
        Jwt jwt = SecurityUtils.getCurrentJwt();
        UUID userId = UUID.fromString(jwt.getClaim("userId"));

        if(clientProfileRepository.existsByUserIdAndCompanyName(userId, request.getCompanyName()))
        {
            throw new AppException(ErrorCode.COMPANY_ALREADY_EXISTS);
        }

        ClientProfile cProfile = ClientProfile.builder()
                .companyName(request.getCompanyName())
                .userId(userId)
                .build();

        ClientProfile savedCProfile = clientProfileRepository.save(cProfile);

        eventPublisher.publishEvent(new ActiveProfileChangedEvent(userId, ProfileType.CLIENT, savedCProfile.getId()));

        return ProfileResponse.<ClientProfile>builder()
                .profile(savedCProfile)
                .profileType(ProfileType.CLIENT)
                .build();
    }

    @Transactional
    public ProfileResponse<?> switchAccount(ProfileType targetType, UUID targetClientProfileId)
    {
        UUID userId = UUID.fromString(SecurityUtils.getCurrentJwt().getClaim("userId"));

        if(targetType == ProfileType.CLIENT)
        {
            if(targetClientProfileId == null)
            {
                throw new AppException(ErrorCode.MISSING_CLIENT_PROFILE_ID);
            }

            ClientProfile cProfile = clientProfileRepository.findByIdAndUserId(targetClientProfileId, userId)
                    .orElseThrow(() -> new AppException(ErrorCode.SETTING_NOT_FOUND));

            eventPublisher.publishEvent(new ActiveProfileChangedEvent(userId, targetType, targetClientProfileId));

            return new ProfileResponse<>(ProfileType.CLIENT, cProfile);
        }
        else
        {
            FreelancerProfile fProfile = freelancerProfileRepository.findByUserId(userId)
                    .orElseThrow(() -> new AppException(ErrorCode.SETTING_NOT_FOUND));

            eventPublisher.publishEvent(new ActiveProfileChangedEvent(userId, targetType, null));

            return new ProfileResponse<>(ProfileType.FREELANCER, fProfile);
        }
    }

    @Override
    public ProfileResponse<?> getCurrentProfile() {

        UUID userId = UUID.fromString(SecurityUtils.getCurrentJwt().getClaim("userId"));

        ProfileType activeType = activeProfileProvider.getActiveProfileType(userId);

        switch (activeType) {

            case FREELANCER -> {
                FreelancerProfile profile = freelancerProfileRepository.findByUserId(userId)
                        .orElseThrow(() -> new AppException(ErrorCode.SETTING_NOT_FOUND));

                FreelancerProfileResponse response = profileMapper.toFreelancerProfileReponse(profile);

                return ProfileResponse.<FreelancerProfileResponse>builder()
                        .profileType(ProfileType.FREELANCER)
                        .profile(response)
                        .build();
            }

            case CLIENT -> {
                ClientProfile profile = clientProfileRepository.findById(
                        activeProfileProvider.getActiveClientProfileId(userId)
                ).orElseThrow(() -> new AppException(ErrorCode.SETTING_NOT_FOUND));

                ClientProfileResponse response = profileMapper.toClientProfileResponse(profile);

                return ProfileResponse.<ClientProfileResponse>builder()
                        .profileType(ProfileType.CLIENT)
                        .profile(response)
                        .build();
            }
        }
        return null; // fallback
    }
}
