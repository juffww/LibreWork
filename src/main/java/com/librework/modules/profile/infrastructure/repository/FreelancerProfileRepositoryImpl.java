package com.librework.modules.profile.infrastructure.repository;

import com.librework.modules.profile.domain.entity.FreelancerProfile;
import com.librework.modules.profile.domain.repository.FreelancerProfileRepository;
import com.librework.modules.profile.infrastructure.mapper.FreelancerProfileEntityMapper;
import com.librework.modules.profile.infrastructure.repository.jpa.FreelancerProfileJpaRepository;
import com.librework.modules.profile.infrastructure.entity.FreelancerProfileJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FreelancerProfileRepositoryImpl implements FreelancerProfileRepository {

    private final FreelancerProfileJpaRepository jpaRepository;
    private final FreelancerProfileEntityMapper entityMapper;

    @Override
    public Optional<FreelancerProfile> findByUserId(UUID userId) {
        return jpaRepository.findByUserId(userId).map(entityMapper::toDomain);
    }

    @Override
    public FreelancerProfile save(FreelancerProfile freelancerProfile) {
        return entityMapper.toDomain(jpaRepository.save(entityMapper.toEntity(freelancerProfile)));
    }

    @Override
    public Optional<FreelancerProfile> findById(UUID id) {
        return jpaRepository.findById(id).map(entityMapper::toDomain);
    }
}

