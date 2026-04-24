package com.librework.modules.identity.infrastructure.adapter;

import com.librework.common.port.ActiveProfilePort;
import com.librework.common.enums.ProfileType;
import com.librework.modules.identity.domain.entity.UserSetting;
import com.librework.modules.identity.domain.repository.UserSettingRepository;
import com.librework.common.exception.AppException;
import com.librework.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ActiveProfileAdapter implements ActiveProfilePort {

    private final UserSettingRepository userSettingRepository;

    @Override
    public void setActiveProfile(UUID userId, ProfileType profileType) {
        var setting = userSettingRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.SETTING_NOT_FOUND));
        setting.switchProfile(profileType);
        userSettingRepository.save(setting);
    }

    @Override
    public ProfileType getActiveProfileType(UUID userId) {
        return userSettingRepository.findById(userId)
                .map(UserSetting::getActiveProfileType)
                .orElse(ProfileType.FREELANCER);
    }
}