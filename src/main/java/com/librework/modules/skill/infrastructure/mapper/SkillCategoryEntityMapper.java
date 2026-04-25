package com.librework.modules.skill.infrastructure.mapper;

import com.librework.modules.skill.domain.entity.SkillCategory;
import com.librework.modules.skill.infrastructure.entity.SkillCategoryJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SkillCategoryEntityMapper {
    SkillCategory toDomain(SkillCategoryJpaEntity entity);
    SkillCategoryJpaEntity toEntity(SkillCategory domain);
}
