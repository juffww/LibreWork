package com.librework.common.port;

import java.util.UUID;

public record SkillInfo(
        UUID id,
        String name,
        String slug
) {}
