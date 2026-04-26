package com.librework.modules.profile.application.dto.response;

import com.librework.modules.profile.domain.entity.ProficiencyLevel;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record FreelancerSkillResponse(
        UUID skillId,
        String skillName,
        String skillSlug,
        ProficiencyLevel proficiencyLevel,
        LocalDateTime createdAt
) {}
