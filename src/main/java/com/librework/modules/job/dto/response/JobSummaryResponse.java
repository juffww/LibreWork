package com.librework.modules.job.dto.response;

import com.librework.common.enums.*;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record JobSummaryResponse(
        UUID id,
        UUID clientId,
        String title,
        String description,
        UUID categoryId,
        BudgetType budgetType,
        BigDecimal budgetFixed,
        BigDecimal budgetMin,
        BigDecimal budgetMax,
        String currency,
        JobDuration duration,
        ExperienceLevel experienceLevel,
        JobStatus status,
        int proposalsCount,
        JobVisibility visibility,
        boolean isUrgent,
        int freelancersNeeded,
        UUID subcategoryId,
        List<JobSkillResponse> skills,
        LocalDateTime createdAt
) {}
