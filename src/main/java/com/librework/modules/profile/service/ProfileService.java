package com.librework.modules.profile.service;

import com.librework.common.enums.ProfileType;
import com.librework.modules.profile.dto.response.UserProfileSummary;

public interface ProfileService {
    UserProfileSummary switchAccount(ProfileType targetType);

    UserProfileSummary getMe();
}
