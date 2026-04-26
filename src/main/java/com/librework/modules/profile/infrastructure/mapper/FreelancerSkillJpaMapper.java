package com.librework.modules.profile.infrastructure.mapper;

import com.librework.modules.profile.domain.entity.FreelancerSkill;
import com.librework.modules.profile.infrastructure.entity.FreelancerSkillJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FreelancerSkillJpaMapper {
    FreelancerSkill toDomain(FreelancerSkillJpaEntity entity);
    FreelancerSkillJpaEntity toEntity(FreelancerSkill domain);
}
