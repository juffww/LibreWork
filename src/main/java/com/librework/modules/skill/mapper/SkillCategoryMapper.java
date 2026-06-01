package com.librework.modules.skill.mapper;

import com.librework.modules.skill.dto.response.SkillCategoryResponse;
import com.librework.modules.skill.entity.SkillCategory;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SkillCategoryMapper {
    SkillCategoryResponse toSkillCategoryResponse(SkillCategory skillCategory);
    List<SkillCategoryResponse> toResponseList(List<SkillCategory> skillCategories);
}
