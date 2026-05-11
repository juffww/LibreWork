package com.librework.modules.job.infrastructure.adapter;

import com.librework.common.exception.AppException;
import com.librework.common.exception.ErrorCode;
import com.librework.common.port.ClientProfileQueryPort;
import com.librework.common.port.FreelancerProfileQueryPort;
import com.librework.modules.profile.infrastructure.entity.ClientProfileJpaEntity;
import com.librework.modules.profile.infrastructure.entity.FreelancerProfileJpaEntity;
import com.librework.modules.profile.infrastructure.repository.jpa.ClientProfileJpaRepository;
import com.librework.modules.profile.infrastructure.repository.jpa.FreelancerProfileJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

// ClientProfileQueryAdapter.java
@Component
@AllArgsConstructor
public class ClientProfileQueryAdapter implements ClientProfileQueryPort {
    private final ClientProfileJpaRepository clientProfileJpaRepository;

    @Override
    public UUID findIdByUserId(UUID userId) {
        return clientProfileJpaRepository.findByUserId(userId)
                .map(ClientProfileJpaEntity::getId)
                .orElseThrow(() -> new RuntimeException());
    }
}

