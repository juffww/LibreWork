package com.librework.modules.profile.infrastructure.repository;

import com.librework.modules.profile.domain.entity.ClientProfile;
import com.librework.modules.profile.domain.repository.ClientProfileRepository;
import com.librework.modules.profile.infrastructure.mapper.ClientProfileEntityMapper;
import com.librework.modules.profile.infrastructure.repository.jpa.ClientProfileJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
@Component
@RequiredArgsConstructor
public class ClientProfileRepositoryImpl implements ClientProfileRepository {

    private final ClientProfileJpaRepository jpaRepository;
    private final ClientProfileEntityMapper mapper;

    @Override
    public Optional<ClientProfile> findByIdAndUserId(UUID id, UUID userId) {
        return jpaRepository.findByIdAndUserId(id, userId).map(mapper::toDomain);
    }

    @Override
    public boolean existsByUserIdAndCompanyName(UUID userId, String companyName) {
        return jpaRepository.existsByUserIdAndCompanyName(userId, companyName);
    }

    @Override
    public ClientProfile save(ClientProfile clientProfile) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(clientProfile)));
    }

    @Override
    public Optional<ClientProfile> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<ClientProfile> findByUserId(UUID userId) {
        return jpaRepository.findByUserId(userId).map(mapper::toDomain);
    }
}

