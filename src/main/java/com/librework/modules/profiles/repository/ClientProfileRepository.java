package com.librework.modules.profiles.repository;

import com.librework.modules.profiles.entity.ClientProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClientProfileRepository extends JpaRepository<ClientProfile, UUID> {
    Optional<ClientProfile> findByIdAndUserId(UUID id, UUID userId);

    boolean existsByUserIdAndCompanyName(UUID userId, String companyName);
}