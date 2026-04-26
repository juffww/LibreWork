package com.librework.modules.profile.infrastructure.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class FreelancerSkillId implements Serializable {
    private UUID freelancerId;
    private UUID skillId;
}
