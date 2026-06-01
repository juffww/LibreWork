package com.librework.modules.skill.service;

import com.librework.modules.skill.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SkillLookupService {
    private final SkillRepository skillRepository;

    public boolean existsById(UUID skillId) {
        return skillRepository.existsById(skillId);
    }

    public Optional<SkillInfo> findById(UUID skillId) {
        return skillRepository.findById(skillId)
                .map(skill -> new SkillInfo(skill.getId(), skill.getName(), skill.getSlug()));
    }

    public List<SkillInfo> findAllByIds(List<UUID> ids) {
        return skillRepository.findAllById(ids).stream()
                .map(skill -> new SkillInfo(skill.getId(), skill.getName(), skill.getSlug()))
                .toList();
    }
}
