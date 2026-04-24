package com.librework.modules.identity.application.service.impl;

import com.librework.common.enums.ProfileType;
import com.librework.common.exception.AppException;
import com.librework.common.exception.ErrorCode;
import com.librework.common.port.CurrentUserPort;
import com.librework.modules.identity.application.dto.request.UserSettingUpdateRequest;
import com.librework.modules.identity.application.dto.response.UserSettingResponse;
import com.librework.modules.identity.domain.entity.UserSetting;
import com.librework.modules.identity.application.mapper.UserSettingMapper;
import com.librework.modules.identity.domain.repository.UserSettingRepository;
import com.librework.modules.identity.application.port.in.UserSettingUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserSettingServiceImpl implements UserSettingUseCase {
    private final UserSettingRepository userSettingRepository;
    private final UserSettingMapper userSettingMapper;
    private final CurrentUserPort currentUserPort;

    @Override
    @Transactional
    public UserSettingResponse updateSetting(UserSettingUpdateRequest request) {
        UUID userId = currentUserPort.getCurrentUserId(); // sửa chỗ này
        UserSetting setting = userSettingRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.SETTING_NOT_FOUND));
        setting.updateSettings(request.getLanguage(), request.getCountry(), request.getTimezone());
        return userSettingMapper.toResponse(userSettingRepository.save(setting));
    }

    @Override
    public UserSettingResponse getUserSetting() {
        UUID userId = currentUserPort.getCurrentUserId(); // sửa chỗ này
        UserSetting setting = userSettingRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.SETTING_NOT_FOUND));
        return userSettingMapper.toResponse(setting);
    }

    @Override
    @Transactional
    public void initDefaultSetting(UUID userId, ProfileType accountType) {
        if (userSettingRepository.findById(userId).isPresent()) {
            return;
        }
        UserSetting defaultSetting = UserSetting.builder()
                .userId(userId)
                .activeProfileType(accountType)
                .build();
        userSettingRepository.save(defaultSetting);
    }
}