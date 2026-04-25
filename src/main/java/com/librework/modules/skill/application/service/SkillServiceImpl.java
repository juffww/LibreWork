package com.librework.modules.skill.application.service;

import com.librework.modules.skill.application.dto.response.SkillResponse;
import com.librework.modules.skill.application.mapper.SkillMapper;
import com.librework.modules.skill.application.port.in.SkillUseCase;
import com.librework.modules.skill.domain.repository.SkillRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SkillServiceImpl implements SkillUseCase {
    private final SkillRepository skillRepository;
    private final SkillMapper mapper;

    @Override
    public SkillResponse getById(UUID id) {
        return mapper.toSkillResponse(skillRepository.findById(id));
    }

    @Override
    public List<SkillResponse> getAll() {
        return mapper.toResponseList(skillRepository.findAll());
    }

    @Override
    public List<SkillResponse> getByCategoryId(UUID categoryId) {
        return mapper.toResponseList(skillRepository.findAllByCategoryId(categoryId));
    }
}
