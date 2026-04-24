package com.librework.modules.profile.application.port.in;

import com.librework.common.enums.ProfileType;
import com.librework.modules.profile.application.dto.response.UserProfileSummary;

public interface ProfileUseCase {
    UserProfileSummary switchAccount(ProfileType targetType);
    UserProfileSummary getMe();
}
