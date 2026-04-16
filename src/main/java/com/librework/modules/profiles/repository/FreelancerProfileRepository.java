package com.librework.modules.profiles.repository;

import com.librework.modules.profiles.entity.FreelancerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FreelancerProfileRepository extends JpaRepository<FreelancerProfile, UUID> {
    Optional<FreelancerProfile> findByUserId(UUID userId);
}