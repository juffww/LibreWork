package com.librework.modules.skill.service;

import com.librework.modules.skill.dto.response.SkillCategoryResponse;

import java.util.List;
import java.util.UUID;

public interface SkillCategoryService {
    SkillCategoryResponse getById(UUID id);

    List<SkillCategoryResponse> getAll();
}
