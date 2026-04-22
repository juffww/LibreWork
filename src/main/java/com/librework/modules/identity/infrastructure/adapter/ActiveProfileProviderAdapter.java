package com.librework.modules.identity.infrastructure.adapter;

import com.librework.common.enums.ProfileType;
import com.librework.common.exception.AppException;
import com.librework.common.exception.ErrorCode;
import com.librework.modules.identity.domain.entity.UserSetting;
import com.librework.modules.identity.domain.repository.UserSettingRepository;
import com.librework.modules.profile.domain.port.ActiveProfileProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ActiveProfileProviderAdapter implements ActiveProfileProvider {

    private final UserSettingRepository userSettingRepository;

    @Override
    public ProfileType getActiveProfileType(UUID userId) {
        UserSetting setting = userSettingRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.SETTING_NOT_FOUND));
        return setting.getActiveProfileType();
    }

    @Override
    public UUID getActiveClientProfileId(UUID userId) {
        UserSetting setting = userSettingRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.SETTING_NOT_FOUND));
        return setting.getActiveClientProfileId();
    }
}

