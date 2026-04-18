package com.librework.modules.profiles.service;

import com.librework.common.ProfileType;
import com.librework.modules.profiles.dto.request.ClientProfileCreationRequest;
import com.librework.modules.profiles.dto.request.UserSettingUpdateRequest;
import com.librework.modules.profiles.dto.response.ProfileResponse;
import com.librework.modules.profiles.entity.UserSetting;

import java.util.UUID;

public interface UserSettingService {
    UserSetting updateSetting(UserSettingUpdateRequest request);
    ProfileResponse createClientProfile(ClientProfileCreationRequest request);
    UserSetting getUserSetting();
    ProfileResponse switchAccount(ProfileType targetType, UUID targetClientProfileId);
}
