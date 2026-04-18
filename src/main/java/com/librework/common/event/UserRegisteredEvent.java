package com.librework.common.event;

import com.librework.common.ProfileType;

import java.util.UUID;

public record UserRegisteredEvent(
        UUID userId,
        String email,
        ProfileType accountType
) {}