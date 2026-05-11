package com.librework.modules.job.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class JobSkill {
    private UUID jobId;
    private UUID skillId;
    private boolean required;
}