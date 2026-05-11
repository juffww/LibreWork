package com.librework.modules.job.infrastructure.entity;

import com.librework.common.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "jobs")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class JobJpaEntity {

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
}