package com.librework.common.event;

import com.librework.common.enums.ProfileType;
import java.util.UUID;

public record ProfileInitializedEvent(
        UUID userId,
        ProfileType accountType
) {}

