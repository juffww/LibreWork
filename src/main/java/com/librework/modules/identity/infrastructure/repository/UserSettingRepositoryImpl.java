package com.librework.modules.identity.infrastructure.repository;

import com.librework.modules.identity.domain.entity.UserSetting;
import com.librework.modules.identity.domain.repository.UserSettingRepository;
import com.librework.modules.identity.infrastructure.repository.jpa.UserSettingJpaRepository;
import com.librework.modules.identity.infrastructure.entity.UserSettingJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserSettingRepositoryImpl implements UserSettingRepository {

    private final UserSettingJpaRepository jpaRepository;

    private UserSetting toDomain(UserSettingJpaEntity entity) {
        if (entity == null) return null;
        return UserSetting.builder()
                .userId(entity.getUserId())
                .timezone(entity.getTimezone())
                .country(entity.getCountry())
                .language(entity.getLanguage())
                .activeProfileType(entity.getActiveProfileType())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    private UserSettingJpaEntity toEntity(UserSetting domain) {
        if (domain == null) return null;
        return UserSettingJpaEntity.builder()
                .userId(domain.getUserId())
                .timezone(domain.getTimezone())
                .country(domain.getCountry())
                .language(domain.getLanguage())
                .activeProfileType(domain.getActiveProfileType())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }

    @Override
    public Optional<UserSetting> findById(UUID id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public UserSetting save(UserSetting userSetting) {
        UserSettingJpaEntity entity = toEntity(userSetting);
        return toDomain(jpaRepository.save(entity));
    }
}
