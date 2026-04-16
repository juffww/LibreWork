package com.librework.modules.profiles.service.Impl;

import com.librework.common.ProfileType;
import com.librework.common.exception.AppException;
import com.librework.common.exception.ErrorCode;
import com.librework.common.security.SecurityUtils;
import com.librework.modules.profiles.dto.request.UserSettingUpdateRequest;
import com.librework.modules.profiles.entity.ClientProfile;
import com.librework.modules.profiles.entity.UserSetting;
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
    public void swtichActiveContext(ProfileType targetType, UUID targetClientProfileId)
    {
        UUID userId = SecurityUtils.getCurrentJwt().getClaim("userId");

        UserSetting setting = userSettingRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.SETTING_NOT_FOUND));

        // Check switch from Freelancer to Client
        if(targetType == ProfileType.CLIENT)
        {
            if(targetClientProfileId == null)
            {
                throw  new AppException(ErrorCode.MISSING_CLIENT_PROFILE_ID);
            }

            ClientProfile cProfile = clientProfileRepository.findByIdAndUserId(targetClientProfileId, userId)
                    .orElseThrow(() -> new AppException(ErrorCode.SETTING_NOT_FOUND));

            setting.setActiveClientProfileId(cProfile.getId());
        }
        else {
            setting.setActiveClientProfileId(null);
        }

        setting.setActiveProfileType(targetType);
        userSettingRepository.save(setting);
    }
}