package com.librework.modules.skill.mapper;

import com.librework.modules.skill.dto.response.SkillResponse;
import com.librework.modules.skill.entity.Skill;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SkillMapper {
    SkillResponse toSkillResponse(Skill skill);
    List<SkillResponse> toResponseList(List<Skill> skills);
}
