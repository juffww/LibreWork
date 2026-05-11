package com.librework.modules.job.application.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record JobCategoryResponse(
        UUID id,
        UUID parentId,
        String name,
        String slug,
        String iconUrl,
        String description,
        int sortOrder,
        LocalDateTime createdAt
) {}