package com.librework.modules.profile.domain.repository;

import com.librework.modules.profile.domain.entity.ClientProfile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientProfileRepository {
    Optional<ClientProfile> findByUserId(UUID userId);

    Optional<ClientProfile> findByIdAndUserId(UUID id, UUID userId);

    boolean existsByUserIdAndCompanyName(UUID userId, String companyName);

    ClientProfile save(ClientProfile clientProfile);

    Optional<ClientProfile> findById(UUID id);
}