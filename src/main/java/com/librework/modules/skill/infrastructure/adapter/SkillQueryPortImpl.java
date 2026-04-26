package com.librework.modules.skill.infrastructure.adapter;

import com.librework.common.port.SkillInfo;
import com.librework.common.port.SkillQueryPort;
import com.librework.modules.skill.infrastructure.repository.jpa.SkillJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SkillQueryPortImpl implements SkillQueryPort {
    private final SkillJpaRepository jpaRepository;

    @Override
    public boolean existsById(UUID skillId) {
        return jpaRepository.existsById(skillId);
    }

    @Override
    public Optional<SkillInfo> findById(UUID skillId) {
        return jpaRepository.findById(skillId)
                .map(e -> new SkillInfo(e.getId(), e.getName(), e.getSlug()));
    }

    @Override
    public List<SkillInfo> findAllByIds(List<UUID> ids) {
        return jpaRepository.findAllByIdIn(ids)
                .stream()
                .map(e -> new SkillInfo(e.getId(), e.getName(), e.getSlug()))
                .toList();
    }
}
