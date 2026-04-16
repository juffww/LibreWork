package com.librework.common.event;

import java.util.UUID;

public record UserRegisteredEvent(
        UUID userId,
        String email,
        com.librework.common.ProfileType accountType
) {}