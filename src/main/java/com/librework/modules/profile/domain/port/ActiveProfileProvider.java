package com.librework.modules.profile.domain.port;

import com.librework.common.enums.ProfileType;
import java.util.UUID;

public interface ActiveProfileProvider {
    ProfileType getActiveProfileType(UUID userId);
    UUID getActiveClientProfileId(UUID userId);
}

