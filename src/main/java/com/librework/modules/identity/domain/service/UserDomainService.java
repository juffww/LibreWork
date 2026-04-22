package com.librework.modules.identity.domain.service;

import com.librework.modules.identity.domain.entity.User;
import com.librework.modules.identity.domain.entity.UserSetting;
import com.librework.common.enums.ProfileType;

import java.util.UUID;

/**
 * Domain Service for complex operations involving User and UserSettings that don't belong in a single entity.
 */
public class UserDomainService {

    /**
     * Toggles the user's active profile context.
     * Complex logic regarding whether a user can switch profiles belongs here.
     */
    public void switchUserProfileContext(User user, UserSetting setting, ProfileType type, UUID clientProfileId) {
        if (user.getStatus() != User.UserStatus.ACTIVE) {
            throw new IllegalStateException("Cannot change settings for inactive users");
        }

        setting.switchProfile(type, clientProfileId);
    }
}

