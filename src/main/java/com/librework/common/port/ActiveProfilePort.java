package com.librework.common.port;

import com.librework.common.enums.ProfileType;
import java.util.UUID;

public interface ActiveProfilePort {
    ProfileType getActiveProfileType(UUID userId);
    void setActiveProfile(UUID userId, ProfileType type);
}