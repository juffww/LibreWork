package com.librework.modules.skill.application.port.in;

import com.librework.modules.skill.application.dto.response.SkillResponse;

import java.util.List;
import java.util.UUID;

public interface SkillUseCase {
    SkillResponse getById(UUID id);
    List<SkillResponse> getAll();
    List<SkillResponse> getByCategoryId(UUID categoryId);
}
