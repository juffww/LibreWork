package com.librework.modules.job.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "job_skills")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
@IdClass(JobSkillId.class)
public class JobSkillJpaEntity {

    @Id
    @Column(name = "job_id")
    private UUID jobId;

    @Id
    @Column(name = "skill_id")
    private UUID skillId;

    @Column(name = "is_required")
    private boolean required;
}