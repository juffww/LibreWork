package com.librework.modules.identity.infrastructure.repository.jpa;

import com.librework.modules.identity.infrastructure.entity.UserSettingJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserSettingJpaRepository extends JpaRepository<UserSettingJpaEntity, UUID> {
}
