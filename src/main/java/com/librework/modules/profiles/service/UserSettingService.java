package com.librework.modules.profiles.service;

import com.librework.modules.profiles.dto.request.UserSettingUpdateRequest;
import com.librework.modules.profiles.entity.UserSetting;

public interface UserSettingService {
    UserSetting updateSetting(UserSettingUpdateRequest request);
}
