package com.librework.modules.profile.infrastructure.repository;

import com.librework.modules.profile.domain.entity.FreelancerProfile;
import com.librework.modules.profile.domain.repository.FreelancerProfileRepository;
import com.librework.modules.profile.infrastructure.repository.jpa.FreelancerProfileJpaRepository;
import com.librework.modules.profile.infrastructure.repository.jpa.entity.FreelancerProfileJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FreelancerProfileRepositoryImpl implements FreelancerProfileRepository {

    private final FreelancerProfileJpaRepository jpaRepository;

    private FreelancerProfile toDomain(FreelancerProfileJpaEntity entity) {
        if (entity == null) return null;
        return FreelancerProfile.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .title(entity.getTitle())
                .hourlyRate(entity.getHourlyRate())
                .experienceLevel(entity.getExperienceLevel())
                .availability(entity.getAvailability())
                .totalEarned(entity.getTotalEarned())
                .overview(entity.getOverview())
                .socialLinks(entity.getSocialLinks())
                .updatedAt(entity.getUpdatedAt())
                .avatarUrl(entity.getAvatarUrl())
                .build();
    }

    private FreelancerProfileJpaEntity toEntity(FreelancerProfile domain) {
        if (domain == null) return null;
        return FreelancerProfileJpaEntity.builder()
                .id(domain.getId())
                .userId(domain.getUserId())
                .title(domain.getTitle())
                .hourlyRate(domain.getHourlyRate())
                .experienceLevel(domain.getExperienceLevel())
                .availability(domain.getAvailability())
                .totalEarned(domain.getTotalEarned())
                .overview(domain.getOverview())
                .socialLinks(domain.getSocialLinks())
                .updatedAt(domain.getUpdatedAt())
                .avatarUrl(domain.getAvatarUrl())
                .build();
    }

    @Override
    public Optional<FreelancerProfile> findByUserId(UUID userId) {
        return jpaRepository.findByUserId(userId).map(this::toDomain);
    }

    @Override
    public FreelancerProfile save(FreelancerProfile freelancerProfile) {
        return toDomain(jpaRepository.save(toEntity(freelancerProfile)));
    }

    @Override
    public Optional<FreelancerProfile> findById(UUID id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }
}

