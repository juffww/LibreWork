package com.librework.modules.identity.application.service;

import com.librework.common.enums.ProfileType;
import com.librework.modules.identity.application.dto.request.UserSettingUpdateRequest;
import com.librework.modules.identity.application.dto.response.UserSettingResponse;

import java.util.UUID;

public interface UserSettingService {
    UserSettingResponse updateSetting(UserSettingUpdateRequest request);
    UserSettingResponse getUserSetting();
    UserSettingResponse getUserSettingByUserId(UUID userId);
    void updateActiveProfile(UUID userId, ProfileType targetType, UUID targetClientProfileId);
}
