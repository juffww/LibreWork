package com.librework.modules.job.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

@Getter
public class JobSkillRequest {
    @NotNull
    private UUID skillId;
    private boolean required = true;
}