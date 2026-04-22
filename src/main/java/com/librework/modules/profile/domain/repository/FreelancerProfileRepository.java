package com.librework.modules.profile.domain.repository;

import com.librework.modules.profile.domain.entity.FreelancerProfile;

import java.util.Optional;
import java.util.UUID;

public interface FreelancerProfileRepository {
    Optional<FreelancerProfile> findByUserId(UUID userId);
    FreelancerProfile save(FreelancerProfile freelancerProfile);
    Optional<FreelancerProfile> findById(UUID id);
}