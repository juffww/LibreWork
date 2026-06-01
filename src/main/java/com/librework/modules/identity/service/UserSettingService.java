package com.librework.modules.identity.service;

import com.librework.common.enums.ProfileType;
import com.librework.modules.identity.dto.request.UserSettingUpdateRequest;
import com.librework.modules.identity.dto.response.UserSettingResponse;

import java.util.UUID;

public interface UserSettingService {
    UserSettingResponse updateSetting(UserSettingUpdateRequest request);

    UserSettingResponse getUserSetting();

    void initDefaultSetting(UUID userId, ProfileType accountType);
}
