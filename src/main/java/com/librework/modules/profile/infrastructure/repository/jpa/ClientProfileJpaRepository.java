package com.librework.modules.profile.infrastructure.repository.jpa;

import com.librework.modules.profile.infrastructure.entity.ClientProfileJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientProfileJpaRepository extends JpaRepository<ClientProfileJpaEntity, UUID> {
    Optional<ClientProfileJpaEntity> findByUserId(UUID userId);

    Optional<ClientProfileJpaEntity> findByIdAndUserId(UUID id, UUID userId);

    boolean existsByUserIdAndCompanyName(UUID userId, String companyName);
}

