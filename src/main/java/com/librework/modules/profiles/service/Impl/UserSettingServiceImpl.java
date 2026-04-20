package com.librework.modules.profiles.service.Impl;

import com.librework.common.ProfileType;
import com.librework.common.exception.AppException;
import com.librework.common.exception.ErrorCode;
import com.librework.common.security.SecurityUtils;
import com.librework.modules.identity.entity.User;
import com.librework.modules.profiles.dto.request.ClientProfileCreationRequest;
import com.librework.modules.profiles.dto.request.UserSettingUpdateRequest;
import com.librework.modules.profiles.dto.response.ClientProfileResponse;
import com.librework.modules.profiles.dto.response.FreelancerReponse;
import com.librework.modules.profiles.dto.response.ProfileResponse;
import com.librework.modules.profiles.entity.ClientProfile;
import com.librework.modules.profiles.entity.FreelancerProfile;
import com.librework.modules.profiles.entity.UserSetting;
import com.librework.modules.profiles.mapper.ProfileMapper;
import com.librework.modules.profiles.mapper.UserSettingMapper;
import com.librework.modules.profiles.repository.ClientProfileRepository;
import com.librework.modules.profiles.repository.FreelancerProfileRepository;
import com.librework.modules.profiles.repository.UserSettingRepository;
import com.librework.modules.profiles.service.UserSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserSettingServiceImpl implements UserSettingService {
    private final UserSettingRepository userSettingRepository;
    private final UserSettingMapper userSettingMapper;
    private final ClientProfileRepository clientProfileRepository;
    private final FreelancerProfileRepository freelancerProfileRepository;
    private final ProfileMapper profileMapper;

    @Transactional
    public UserSetting updateSetting(UserSettingUpdateRequest request)
    {
        Jwt jwt = SecurityUtils.getCurrentJwt();

        UUID userId = UUID.fromString(jwt.getClaim("userId"));

        UserSetting userSetting = userSettingRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User setting not found"));

        userSettingMapper.toUpdateSetting(userSetting, request);

        return userSettingRepository.save(userSetting);
    }

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
        // setting
        UserSetting userSetting = userSettingRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.SETTING_NOT_FOUND));

        userSetting.setActiveProfileType(ProfileType.CLIENT);
        userSetting.setActiveClientProfileId(savedCProfile.getId());
        userSettingRepository.save(userSetting);

        return ProfileResponse.<ClientProfile>builder()
                .profile(savedCProfile)
                .profileType(ProfileType.CLIENT)
                .build();
    }

    @Transactional
    public ProfileResponse<?> switchAccount(ProfileType targetType, UUID targetClientProfileId)
    {
        UUID userId = UUID.fromString(SecurityUtils.getCurrentJwt().getClaim("userId"));

        UserSetting setting = userSettingRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.SETTING_NOT_FOUND));

        if(targetType == ProfileType.CLIENT)
        {
            if(targetClientProfileId == null)
            {
                throw new AppException(ErrorCode.MISSING_CLIENT_PROFILE_ID);
            }

            ClientProfile cProfile = clientProfileRepository.findByIdAndUserId(targetClientProfileId, userId)
                    .orElseThrow(null);

            setting.setActiveClientProfileId(targetClientProfileId);
            setting.setActiveProfileType(targetType);

            userSettingRepository.save(setting);
            return new ProfileResponse<>(ProfileType.CLIENT, cProfile);
        }
        else
        {
            setting.setActiveClientProfileId(null);
            setting.setActiveProfileType(targetType);
            FreelancerProfile fProfile = freelancerProfileRepository.findByUserId(userId)
                    .orElseThrow(null);
            userSettingRepository.save(setting);
            return new ProfileResponse<>(ProfileType.FREELANCER, fProfile);
        }
    }

    @Override
    public UserSetting getUserSetting()
    {
        UUID userId = UUID.fromString(SecurityUtils.getCurrentJwt().getClaim("userId"));
        return userSettingRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.SETTING_NOT_FOUND));

    }

    @Override
    public ProfileResponse<?> getCurrentProfile() {

        UUID userId = UUID.fromString(SecurityUtils.getCurrentJwt().getClaim("userId"));

        UserSetting setting = userSettingRepository.findById(userId)
                .orElseThrow(null);

        switch (setting.getActiveProfileType()) {

            case FREELANCER -> {
                FreelancerProfile profile = freelancerProfileRepository.findByUserId(userId)
                        .orElseThrow(null);

                FreelancerReponse response = profileMapper.toFreelancerProfileReponse(profile);

                return ProfileResponse.<FreelancerReponse>builder()
                        .profileType(ProfileType.FREELANCER)
                        .profile(response)
                        .build();
            }

            case CLIENT -> {
                ClientProfile profile = clientProfileRepository.findById(
                        setting.getActiveClientProfileId()
                ).orElseThrow(null);

                ClientProfileResponse response = profileMapper.toClientProfileResponse(profile);

                return ProfileResponse.<ClientProfileResponse>builder()
                        .profileType(ProfileType.CLIENT)
                        .profile(response)
                        .build();
            }
        }
        return null;
    }
}