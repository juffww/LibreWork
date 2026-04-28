package com.librework.modules.profile.domain.event;

import com.librework.common.enums.ProfileType;
import java.util.UUID;

public record ProfileInitializedEvent(
        UUID userId,
        ProfileType accountType
) {}

