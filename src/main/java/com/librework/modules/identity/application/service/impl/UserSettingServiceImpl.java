package com.librework.modules.identity.application.service.impl;

import com.librework.common.enums.ProfileType;
import com.librework.common.exception.AppException;
import com.librework.common.exception.ErrorCode;
import com.librework.infrastructure.security.SecurityUtils;
import com.librework.modules.identity.application.dto.request.UserSettingUpdateRequest;
import com.librework.modules.identity.application.dto.response.UserSettingResponse;
import com.librework.modules.identity.domain.entity.UserSetting;
import com.librework.modules.identity.application.mapper.UserSettingMapper;
import com.librework.modules.identity.domain.repository.UserSettingRepository;
import com.librework.modules.identity.application.service.UserSettingService;
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

    @Transactional
    public UserSettingResponse updateSetting(UserSettingUpdateRequest request)
    {
        Jwt jwt = SecurityUtils.getCurrentJwt();

        UUID userId = UUID.fromString(jwt.getClaim("userId"));

        UserSetting userSetting = userSettingRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User setting not found"));

        userSettingMapper.toUpdateSetting(userSetting, request);

        return userSettingMapper.toResponse(userSettingRepository.save(userSetting));
    }

    @Transactional
    public void updateActiveProfile(UUID userId, ProfileType targetType, UUID targetClientProfileId)
    {
        UserSetting setting = userSettingRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.SETTING_NOT_FOUND));

        setting.switchProfile(targetType, targetClientProfileId);

        userSettingRepository.save(setting);
    }

    @Override
    public UserSettingResponse getUserSetting()
    {
        UUID userId = UUID.fromString(SecurityUtils.getCurrentJwt().getClaim("userId"));
        UserSetting setting = userSettingRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.SETTING_NOT_FOUND));
        return userSettingMapper.toResponse(setting);
    }

    public UserSettingResponse getUserSettingByUserId(UUID userId) {
        UserSetting setting = userSettingRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.SETTING_NOT_FOUND));
        return userSettingMapper.toResponse(setting);
    }
}