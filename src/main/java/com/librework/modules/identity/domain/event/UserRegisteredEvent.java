package com.librework.modules.identity.domain.event;

import com.librework.common.enums.ProfileType;

import java.util.UUID;

public record UserRegisteredEvent(
        UUID userId,
        String email,
        ProfileType accountType
) {}
