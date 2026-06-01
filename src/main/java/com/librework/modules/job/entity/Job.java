package com.librework.modules.job.entity;

import com.librework.common.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "jobs")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Job {
    @Id
    private UUID id;

    @Column(name = "client_id", nullable = false)
    private UUID clientId;

    @Column(name = "category_id")
    private UUID categoryId;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, columnDefinition = "text")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "job_type", nullable = false, length = 20)
    private JobType jobType;

    @Enumerated(EnumType.STRING)
    @Column(name = "budget_type", nullable = false, length = 10)
    private BudgetType budgetType;

    @Column(name = "budget_fixed", precision = 12, scale = 2)
    private BigDecimal budgetFixed;

    @Column(name = "budget_min", precision = 12, scale = 2)
    private BigDecimal budgetMin;

    @Column(name = "budget_max", precision = 12, scale = 2)
    private BigDecimal budgetMax;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private JobDuration duration;

    @Enumerated(EnumType.STRING)
    @Column(name = "experience_level", nullable = false, length = 20)
    private ExperienceLevel experienceLevel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private JobStatus status;

    @Column(name = "proposals_count")
    private int proposalsCount;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private JobVisibility visibility = JobVisibility.PUBLIC;

    @Builder.Default
    @Column(name = "is_urgent", nullable = false)
    private boolean isUrgent = false;

    @Builder.Default
    @Column(name = "freelancers_needed", nullable = false)
    private int freelancersNeeded = 1;

    @Column(name = "subcategory_id")
    private UUID subcategoryId;

    @Transient
    @Builder.Default
    private List<JobSkill> skills = new ArrayList<>();

    public static Job create(
            UUID clientId, UUID categoryId, String title, String description,
            JobType jobType, BudgetType budgetType, BigDecimal budgetFixed,
            BigDecimal budgetMin, BigDecimal budgetMax, JobDuration duration,
            ExperienceLevel experienceLevel, JobVisibility visibility,
            boolean isUrgent, int freelancersNeeded, UUID subcategoryId
    ) {
        return Job.builder()
                .id(UUID.randomUUID())
                .clientId(clientId)
                .categoryId(categoryId)
                .title(title)
                .description(description)
                .jobType(jobType)
                .budgetType(budgetType)
                .budgetFixed(budgetFixed)
                .budgetMin(budgetMin)
                .budgetMax(budgetMax)
                .duration(duration)
                .experienceLevel(experienceLevel)
                .status(JobStatus.OPEN)
                .proposalsCount(0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .visibility(visibility != null ? visibility : JobVisibility.PUBLIC)
                .isUrgent(isUrgent)
                .freelancersNeeded(freelancersNeeded > 0 ? freelancersNeeded : 1)
                .subcategoryId(subcategoryId)
                .skills(new ArrayList<>())
                .build();
    }

    public void addSkill(UUID skillId, boolean required) {
        boolean alreadyAdded = skills.stream().anyMatch(s -> s.getSkillId().equals(skillId));
        if (alreadyAdded) return;
        skills.add(JobSkill.builder().jobId(this.id).skillId(skillId).required(required).build());
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

    public void replaceSkills(List<JobSkill> skills) {
        this.skills = new ArrayList<>(skills);
    }
}
