package com.librework.modules.job.dto.request;

import com.librework.common.enums.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
public class JobCreationRequest {
    private UUID categoryId;

    @NotBlank
    @Size(max = 200)
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private BudgetType budgetType;

    private BigDecimal budgetFixed;
    private BigDecimal budgetMin;
    private BigDecimal budgetMax;

    @NotNull
    private JobDuration duration;

    @NotNull
    private ExperienceLevel experienceLevel;

    private JobVisibility visibility = JobVisibility.PUBLIC;

    private boolean isUrgent = false;

    private int freelancersNeeded = 1;

    private UUID subcategoryId;

    private String currency;

    // danh sách skill yêu cầu
    private List<JobSkillRequest> skills;
}