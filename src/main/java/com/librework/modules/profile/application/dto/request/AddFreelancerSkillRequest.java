package com.librework.modules.profile.application.dto.request;

import com.librework.modules.profile.domain.entity.ProficiencyLevel;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

@Getter
public class AddFreelancerSkillRequest {
    @NotNull
    UUID skillId;

    @NotNull
    ProficiencyLevel proficiencyLevel;
}
