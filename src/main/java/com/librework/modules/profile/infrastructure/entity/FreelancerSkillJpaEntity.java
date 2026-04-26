package com.librework.modules.profile.infrastructure.entity;

import com.librework.modules.profile.domain.entity.ProficiencyLevel;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "freelancer_skills")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(FreelancerSkillId.class)
public class FreelancerSkillJpaEntity {

    @Id
    @Column(name = "freelancer_id")
    private UUID freelancerId;

    @Id
    @Column(name = "skill_id")
    private UUID skillId;

    @Column(name = "proficiency_level")
    @Enumerated(EnumType.STRING)
    private ProficiencyLevel proficiencyLevel;

    @Column(name = "years_of_experience")
    private Integer yearsOfExperience;

    private LocalDateTime createdAt;
}
