package com.librework.modules.skill.domain.repository;

import com.librework.modules.skill.domain.entity.Skill;

import java.util.List;
import java.util.UUID;

public interface SkillRepository {
    Skill findById(UUID id);
    List<Skill> findAll();
    List<Skill> findAllByCategoryId(UUID categoryId);
}
