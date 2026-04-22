package com.librework.modules.identity.infrastructure.repository;

import com.librework.modules.identity.domain.entity.User;
import com.librework.modules.identity.domain.repository.UserRepository;
import com.librework.modules.identity.infrastructure.repository.jpa.UserJpaRepository;
import com.librework.modules.identity.infrastructure.entity.UserJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpaRepository;

    private User toDomain(UserJpaEntity entity) {
        if (entity == null) return null;
        return User.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .fullName(entity.getFullName())
                .username(entity.getUsername())
                .status(User.UserStatus.valueOf(entity.getStatus().name()))
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    private UserJpaEntity toEntity(User domain) {
        if (domain == null) return null;
        return UserJpaEntity.builder()
                .id(domain.getId())
                .email(domain.getEmail())
                .password(domain.getPassword())
                .fullName(domain.getFullName())
                .username(domain.getUsername())
                .status(UserJpaEntity.UserStatus.valueOf(domain.getStatus().name()))
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return jpaRepository.existsByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(this::toDomain);
    }

    @Override
    public User save(User user) {
        UserJpaEntity entity = toEntity(user);
        UserJpaEntity savedEntity = jpaRepository.save(entity);
        return toDomain(savedEntity);
    }
}
