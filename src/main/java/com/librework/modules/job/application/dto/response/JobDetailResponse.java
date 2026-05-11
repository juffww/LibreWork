package com.librework.modules.job.application.dto.response;

import com.librework.common.enums.*;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record JobDetailResponse(
        UUID id,
        UUID clientId,
        UUID categoryId,
        String title,
        String description,
        JobType jobType,
        BudgetType budgetType,
        BigDecimal budgetFixed,
        BigDecimal budgetMin,
        BigDecimal budgetMax,
        JobDuration duration,
        ExperienceLevel experienceLevel,
        JobStatus status,
        int proposalsCount,
        List<JobSkillResponse> skills,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}