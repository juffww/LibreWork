package com.librework.modules.job.dto.response;

import com.librework.common.enums.*;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record JobSummaryResponse(
        UUID id,
        UUID clientId,
        String title,
        JobType jobType,
        BudgetType budgetType,
        BigDecimal budgetFixed,
        BigDecimal budgetMin,
        BigDecimal budgetMax,
        JobDuration duration,
        ExperienceLevel experienceLevel,
        JobStatus status,
        int proposalsCount,
        JobVisibility visibility,
        boolean isUrgent,
        int freelancersNeeded,
        UUID subcategoryId,
        LocalDateTime createdAt
) {}