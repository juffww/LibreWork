package com.librework.modules.skill.application.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record SkillResponse (
    UUID id,
    UUID categoryId,
    String name,
    String slug,
    boolean isVerified,
    LocalDateTime createdAt
) {}
