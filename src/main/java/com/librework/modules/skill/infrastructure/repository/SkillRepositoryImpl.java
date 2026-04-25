package com.librework.modules.skill.infrastructure.repository;

import com.librework.modules.skill.domain.entity.Skill;
import com.librework.modules.skill.domain.repository.SkillRepository;
import com.librework.modules.skill.infrastructure.mapper.SkillEntityMapper;
import com.librework.modules.skill.infrastructure.repository.jpa.SkillJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class SkillRepositoryImpl implements SkillRepository {
    private final SkillJpaRepository jpaRepository;
    private final SkillEntityMapper mapper;

    @Override
    public Skill findById(UUID id) {
        return mapper.toDomain(jpaRepository.findById(id).orElseThrow());
    }

    @Override
    public List<Skill> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Skill> findAllByCategoryId(UUID categoryId) {
        return jpaRepository.findAllByCategoryId(categoryId).stream().map(mapper::toDomain).toList();
    }
}
