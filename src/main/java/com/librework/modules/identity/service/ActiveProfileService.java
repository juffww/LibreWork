package com.librework.modules.identity.service;

import com.librework.common.enums.ProfileType;
import com.librework.exception.AppException;
import com.librework.exception.ErrorCode;
import com.librework.modules.identity.entity.UserSetting;
import com.librework.modules.identity.repository.UserSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActiveProfileService {
    private final UserSettingRepository userSettingRepository;

    public void setActiveProfile(UUID userId, ProfileType profileType) {
        UserSetting setting = userSettingRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.SETTING_NOT_FOUND));
        setting.switchProfile(profileType);
        userSettingRepository.save(setting);
    }

    public ProfileType getActiveProfileType(UUID userId) {
        return userSettingRepository.findById(userId)
                .map(UserSetting::getActiveProfileType)
                .orElse(ProfileType.FREELANCER);
    }
}
