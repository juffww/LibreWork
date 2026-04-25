package com.librework.modules.skill.domain.repository;

import com.librework.modules.skill.domain.entity.SkillCategory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SkillCategoryRepository {
    List<SkillCategory> findAll();
    Optional<SkillCategory> findById(UUID id);
}
