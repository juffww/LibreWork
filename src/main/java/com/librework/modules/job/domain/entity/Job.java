package com.librework.modules.job.domain.entity;

import com.librework.common.enums.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class Job {
    private UUID id;
    private UUID clientId;          // FK → client_profiles.id
    private UUID categoryId;        // nullable
    private String title;
    private String description;
    private JobType jobType;
    private BudgetType budgetType;
    private BigDecimal budgetFixed; // dùng khi budgetType = FIXED
    private BigDecimal budgetMin;   // dùng khi budgetType = RANGE
    private BigDecimal budgetMax;   // dùng khi budgetType = RANGE
    private JobDuration duration;
    private ExperienceLevel experienceLevel;
    private JobStatus status;
    private int proposalsCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder.Default
    private List<JobSkill> skills = new ArrayList<>();

    // --- business methods ---

    public void addSkill(UUID skillId, boolean required) {
        boolean alreadyAdded = skills.stream()
                .anyMatch(s -> s.getSkillId().equals(skillId));
        if (alreadyAdded) return;
        skills.add(JobSkill.builder()
                .jobId(this.id)
                .skillId(skillId)
                .required(required)
                .build());
    }

    public void close() {
        this.status = JobStatus.CANCELLED;
        this.updatedAt = LocalDateTime.now();
    }

    public void markInProgress() {
        this.status = JobStatus.IN_PROGRESS;
        this.updatedAt = LocalDateTime.now();
    }

    public void markCompleted() {
        this.status = JobStatus.COMPLETED;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isOwner(UUID clientId) {
        return this.clientId.equals(clientId);
    }

    public List<JobSkill> getSkills() {
        return Collections.unmodifiableList(skills);
    }
}