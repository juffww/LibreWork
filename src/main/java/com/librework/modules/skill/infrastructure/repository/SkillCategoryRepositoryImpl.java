package com.librework.modules.skill.infrastructure.repository;

import com.librework.modules.skill.domain.entity.SkillCategory;
import com.librework.modules.skill.domain.repository.SkillCategoryRepository;
import com.librework.modules.skill.infrastructure.mapper.SkillCategoryEntityMapper;
import com.librework.modules.skill.infrastructure.repository.jpa.SkillCategoryJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class SkillCategoryRepositoryImpl implements SkillCategoryRepository {
    private final SkillCategoryJpaRepository jpaRepository;
    private final SkillCategoryEntityMapper mapper;

    @Override
    public List<SkillCategory> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<SkillCategory> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }
}
