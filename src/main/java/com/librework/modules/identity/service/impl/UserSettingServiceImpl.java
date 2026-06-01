package com.librework.modules.identity.service.impl;

import com.librework.modules.identity.service.UserSettingService;



import com.librework.common.enums.ProfileType;
import com.librework.exception.AppException;
import com.librework.exception.ErrorCode;
import com.librework.modules.identity.service.CurrentUserService;
import com.librework.modules.identity.dto.request.UserSettingUpdateRequest;
import com.librework.modules.identity.dto.response.UserSettingResponse;
import com.librework.modules.identity.entity.UserSetting;
import com.librework.modules.identity.mapper.UserSettingMapper;
import com.librework.modules.identity.repository.UserSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserSettingServiceImpl implements UserSettingService {
    private final UserSettingRepository userSettingRepository;
    private final UserSettingMapper userSettingMapper;
    private final CurrentUserService currentUserService;

    @Transactional
    public UserSettingResponse updateSetting(UserSettingUpdateRequest request) {
        UUID userId = currentUserService.getCurrentUserId(); // sửa chỗ này
        UserSetting setting = userSettingRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.SETTING_NOT_FOUND));
        setting.updateSettings(request.getLanguage(), request.getCountry(), request.getTimezone());
        return userSettingMapper.toResponse(userSettingRepository.save(setting));
    }

    public UserSettingResponse getUserSetting() {
        UUID userId = currentUserService.getCurrentUserId(); // sửa chỗ này
        UserSetting setting = userSettingRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.SETTING_NOT_FOUND));
        return userSettingMapper.toResponse(setting);
    }

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
