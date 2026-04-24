package com.librework.modules.profile.infrastructure.repository.jpa;

import com.librework.modules.profile.infrastructure.entity.FreelancerProfileJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FreelancerProfileJpaRepository extends JpaRepository<FreelancerProfileJpaEntity, UUID> {
    Optional<FreelancerProfileJpaEntity> findByUserId(UUID userId);
}

