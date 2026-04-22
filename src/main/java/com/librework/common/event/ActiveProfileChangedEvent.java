package com.librework.common.event;

import com.librework.common.enums.ProfileType;
import java.util.UUID;

public record ActiveProfileChangedEvent(
        UUID userId,
        ProfileType targetType,
        UUID targetClientProfileId
) {}

