package com.librework.modules.job.infrastructure.entity;

import lombok.*;
import java.io.Serializable;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class JobSkillId implements Serializable {
    private UUID jobId;
    private UUID skillId;
}