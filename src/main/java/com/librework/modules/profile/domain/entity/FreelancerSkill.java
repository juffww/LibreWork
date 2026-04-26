package com.librework.modules.profile.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class FreelancerSkill {
    private UUID freelancerId;
    private UUID skillId;
    private ProficiencyLevel proficiencyLevel;
    private LocalDateTime createdAt;

    public void updateProficiency(ProficiencyLevel level) {
        this.proficiencyLevel = level;
    }
}
