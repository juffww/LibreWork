package com.librework.modules.skill.application.mapper;

import com.librework.modules.skill.application.dto.response.SkillCategoryResponse;
import com.librework.modules.skill.domain.entity.SkillCategory;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SkillCategoryMapper {
    SkillCategoryResponse toSkillCategoryResponse(SkillCategory skillCategory);
    List<SkillCategoryResponse> toResponseList(List<SkillCategory> skillCategories);
}
