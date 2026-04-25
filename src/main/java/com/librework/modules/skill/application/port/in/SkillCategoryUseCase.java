package com.librework.modules.skill.application.port.in;

import com.librework.modules.skill.application.dto.response.SkillCategoryResponse;

import java.util.List;
import java.util.UUID;

public interface SkillCategoryUseCase {
    SkillCategoryResponse getById(UUID id);
    List<SkillCategoryResponse> getAll();
}
