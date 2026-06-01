package com.librework.modules.skill.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record SkillCategoryResponse(
        UUID id,
        String name,
        String slug,
        String iconUrl,
        int sortOrder,
        LocalDateTime createdAt
) {}
