package com.librework.modules.profile.repository;

import com.librework.modules.profile.entity.ClientProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientProfileRepository extends JpaRepository<ClientProfile, UUID> {
    Optional<ClientProfile> findByUserId(UUID userId);
    Optional<ClientProfile> findByIdAndUserId(UUID id, UUID userId);
    boolean existsByUserIdAndCompanyName(UUID userId, String companyName);
}
