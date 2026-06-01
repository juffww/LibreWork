package com.librework.modules.skill.service;

import com.librework.modules.skill.dto.response.SkillResponse;

import java.util.List;
import java.util.UUID;

public interface SkillService {
    SkillResponse getById(UUID id);

    List<SkillResponse> getAll();

    List<SkillResponse> getByCategoryId(UUID categoryId);
}
