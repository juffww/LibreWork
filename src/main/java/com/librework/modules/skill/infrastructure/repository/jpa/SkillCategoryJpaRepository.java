package com.librework.modules.skill.infrastructure.repository.jpa;

import com.librework.modules.skill.infrastructure.entity.SkillCategoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SkillCategoryJpaRepository extends JpaRepository<SkillCategoryJpaEntity, UUID> {

}
