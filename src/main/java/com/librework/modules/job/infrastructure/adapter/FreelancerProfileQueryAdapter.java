package com.librework.modules.job.infrastructure.adapter;

import com.librework.common.port.FreelancerProfileQueryPort;
import com.librework.modules.profile.infrastructure.entity.FreelancerProfileJpaEntity;
import com.librework.modules.profile.infrastructure.repository.jpa.FreelancerProfileJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

// FreelancerProfileQueryAdapter.java
@Component
@AllArgsConstructor
public class FreelancerProfileQueryAdapter implements FreelancerProfileQueryPort {
    private final FreelancerProfileJpaRepository freelancerProfileJpaRepository;

    @Override
    public UUID findIdByUserId(UUID userId) {
        return freelancerProfileJpaRepository.findByUserId(userId)
                .map(FreelancerProfileJpaEntity::getId)
                .orElseThrow(() -> new RuntimeException());
    }
}
