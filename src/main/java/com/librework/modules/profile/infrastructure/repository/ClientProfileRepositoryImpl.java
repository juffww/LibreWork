package com.librework.modules.profile.infrastructure.repository;

import com.librework.modules.profile.domain.entity.ClientProfile;
import com.librework.modules.profile.domain.repository.ClientProfileRepository;
import com.librework.modules.profile.infrastructure.repository.jpa.ClientProfileJpaRepository;
import com.librework.modules.profile.infrastructure.repository.jpa.entity.ClientProfileJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ClientProfileRepositoryImpl implements ClientProfileRepository {

    private final ClientProfileJpaRepository jpaRepository;

    private ClientProfile toDomain(ClientProfileJpaEntity entity) {
        if (entity == null) return null;
        return ClientProfile.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .companyName(entity.getCompanyName())
                .logoUrl(entity.getLogoUrl())
                .industry(entity.getIndustry())
                .description(entity.getDescription())
                .websiteUrl(entity.getWebsiteUrl())
                .totalSpent(entity.getTotalSpent())
                .paymentVerified(entity.getPaymentVerified())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    private ClientProfileJpaEntity toEntity(ClientProfile domain) {
        if (domain == null) return null;
        return ClientProfileJpaEntity.builder()
                .id(domain.getId())
                .userId(domain.getUserId())
                .companyName(domain.getCompanyName())
                .logoUrl(domain.getLogoUrl())
                .industry(domain.getIndustry())
                .description(domain.getDescription())
                .websiteUrl(domain.getWebsiteUrl())
                .totalSpent(domain.getTotalSpent())
                .paymentVerified(domain.getPaymentVerified())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }

    @Override
    public Optional<ClientProfile> findByIdAndUserId(UUID id, UUID userId) {
        return jpaRepository.findByIdAndUserId(id, userId).map(this::toDomain);
    }

    @Override
    public boolean existsByUserIdAndCompanyName(UUID userId, String companyName) {
        return jpaRepository.existsByUserIdAndCompanyName(userId, companyName);
    }

    @Override
    public List<ClientProfile> findAllByUserId(UUID userId) {
        return jpaRepository.findAllByUserId(userId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public ClientProfile save(ClientProfile clientProfile) {
        return toDomain(jpaRepository.save(toEntity(clientProfile)));
    }

    @Override
    public Optional<ClientProfile> findById(UUID id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }
}

