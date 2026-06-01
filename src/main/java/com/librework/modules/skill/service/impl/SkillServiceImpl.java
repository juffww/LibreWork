package com.librework.modules.skill.service.impl;

import com.librework.modules.skill.service.SkillService;



import com.librework.modules.skill.dto.response.SkillResponse;
import com.librework.modules.skill.mapper.SkillMapper;
import com.librework.modules.skill.repository.SkillRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SkillServiceImpl implements SkillService {
    private final SkillRepository skillRepository;
    private final SkillMapper mapper;

    public SkillResponse getById(UUID id) {
        return mapper.toSkillResponse(skillRepository.findById(id).orElseThrow());
    }

    public List<SkillResponse> getAll() {
        return mapper.toResponseList(skillRepository.findAll());
    }

    public List<SkillResponse> getByCategoryId(UUID categoryId) {
        return mapper.toResponseList(skillRepository.findAllByCategoryId(categoryId));
    }
}
