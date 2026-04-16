package com.librework.modules.profiles.service.Impl;

import com.librework.common.ProfileType;
import com.librework.common.exception.AppException;
import com.librework.common.exception.ErrorCode;
import com.librework.common.security.SecurityUtils;
import com.librework.modules.profiles.dto.request.ClientProfileCreationRequest;
import com.librework.modules.profiles.dto.request.UserSettingUpdateRequest;
import com.librework.modules.profiles.dto.response.ProfileResponse;
import com.librework.modules.profiles.entity.ClientProfile;
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
    public ProfileResponse createClientProfile(ClientProfileCreationRequest request)
    {
        UUID userId = UUID.fromString(SecurityUtils.getCurrentJwt().getClaim("userId"));

        if (clientProfileRepository.existsByUserIdAndCompanyName(userId, request.getCompanyName())) {
            throw new AppException(ErrorCode.COMPANY_ALREADY_EXISTS);
        }

        ClientProfile clientProfile = profileMapper.toClientProfile(request);
        clientProfile.setUserId(userId);
        clientProfile.setCompanyName(request.getCompanyName());

        ClientProfile savedProfile = clientProfileRepository.save(clientProfile);

        UserSetting userSetting = userSettingRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.SETTING_NOT_FOUND));

        userSetting.setActiveProfileType(ProfileType.CLIENT);
        userSetting.setActiveClientProfileId(savedProfile.getId());
        userSettingRepository.save(userSetting);

        return profileMapper.toProfileResponse(savedProfile);
    }

    @Transactional
    public ProfileResponse switchActiveContext(ProfileType targetType, UUID targetClientProfileId)
    {
        if (targetType == null) {
            throw new IllegalArgumentException("Target profile type cannot be null");
        }

        UUID userId = UUID.fromString(SecurityUtils.getCurrentJwt().getClaim("userId"));

        UserSetting setting = userSettingRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.SETTING_NOT_FOUND));

        ProfileResponse response;

        // Check switch from Freelancer to Client
        if(targetType == ProfileType.CLIENT)
        {
            if(targetClientProfileId == null)
            {
                throw new AppException(ErrorCode.MISSING_CLIENT_PROFILE_ID);
            }

            ClientProfile cProfile = clientProfileRepository.findByIdAndUserId(targetClientProfileId, userId)
                    .orElseThrow(() -> new AppException(ErrorCode.SETTING_NOT_FOUND));

            setting.setActiveClientProfileId(cProfile.getId());
            response = profileMapper.toProfileResponse(cProfile);
        }
        else {
            setting.setActiveClientProfileId(null);
            com.librework.modules.profiles.entity.FreelancerProfile fProfile = freelancerProfileRepository.findByUserId(userId)
                    .orElseThrow(() -> new AppException(ErrorCode.SETTING_NOT_FOUND));
            response = profileMapper.toProfileResponse(fProfile);
        }

        setting.setActiveProfileType(targetType);
        userSettingRepository.save(setting);

        return response;
    }

    @Override
    public UserSetting getUserSetting()
    {
        UUID userId = UUID.fromString(SecurityUtils.getCurrentJwt().getClaim("userId"));
        return userSettingRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.SETTING_NOT_FOUND));

    }
}