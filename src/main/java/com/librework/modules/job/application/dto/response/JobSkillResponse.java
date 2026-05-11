package com.librework.modules.job.application.dto.response;

import lombok.Builder;

import java.util.UUID;

@Builder
public record JobSkillResponse(
        UUID skillId,
        String skillName,
        String skillSlug,
        boolean required
) {}