package com.librework.modules.skill.infrastructure.mapper;

import com.librework.modules.skill.domain.entity.Skill;
import com.librework.modules.skill.infrastructure.entity.SkillJpaEntity;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface SkillEntityMapper {
    Skill toDomain(SkillJpaEntity entity);
    SkillJpaEntity toEntity(Skill domain);
}
