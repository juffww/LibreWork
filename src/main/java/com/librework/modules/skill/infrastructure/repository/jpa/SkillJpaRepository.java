package com.librework.modules.skill.infrastructure.repository.jpa;

import com.librework.modules.skill.infrastructure.entity.SkillJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SkillJpaRepository extends JpaRepository<SkillJpaEntity, UUID> {
    List<SkillJpaEntity> findAllByCategoryId(UUID categoryId);
    List<SkillJpaEntity> findAllByIdIn(List<UUID> ids);
}
