package com.librework.modules.identity.application.port.in;

import com.librework.common.enums.ProfileType;
import com.librework.modules.identity.application.dto.request.UserSettingUpdateRequest;
import com.librework.modules.identity.application.dto.response.UserSettingResponse;

import java.util.UUID;

public interface UserSettingUseCase {
    UserSettingResponse updateSetting(UserSettingUpdateRequest request);
    UserSettingResponse getUserSetting();
    void initDefaultSetting(UUID userId, ProfileType accountType);
}
