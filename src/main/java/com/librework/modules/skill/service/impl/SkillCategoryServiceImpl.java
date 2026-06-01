package com.librework.modules.skill.service.impl;

import com.librework.modules.skill.service.SkillCategoryService;



import com.librework.modules.skill.dto.response.SkillCategoryResponse;
import com.librework.modules.skill.mapper.SkillCategoryMapper;
import com.librework.modules.skill.entity.SkillCategory;
import com.librework.modules.skill.repository.SkillCategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SkillCategoryServiceImpl implements SkillCategoryService {
    private final SkillCategoryMapper mapper;
    private final SkillCategoryRepository categoryRepository;

    public SkillCategoryResponse getById(UUID id) {
        return mapper.toSkillCategoryResponse(categoryRepository.findById(id).orElseThrow());
    }

    public List<SkillCategoryResponse> getAll() {
        return mapper.toResponseList(categoryRepository.findAll());
    }
}
