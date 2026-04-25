package com.librework.modules.skill.application.service;

import com.librework.modules.skill.application.dto.response.SkillCategoryResponse;
import com.librework.modules.skill.application.mapper.SkillCategoryMapper;
import com.librework.modules.skill.application.port.in.SkillCategoryUseCase;
import com.librework.modules.skill.domain.entity.SkillCategory;
import com.librework.modules.skill.domain.repository.SkillCategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SkillCategoryServiceImpl implements SkillCategoryUseCase {
    private final SkillCategoryMapper mapper;
    private final SkillCategoryRepository categoryRepository;

    @Override
    public SkillCategoryResponse getById(UUID id) {
        return mapper.toSkillCategoryResponse(categoryRepository.findById(id).orElseThrow());
    }

    @Override
    public List<SkillCategoryResponse> getAll() {
        return mapper.toResponseList(categoryRepository.findAll());
    }
}
