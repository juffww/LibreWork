package com.librework.modules.profile.infrastructure.repository;

import com.librework.modules.profile.domain.entity.FreelancerSkill;
import com.librework.modules.profile.domain.repository.FreelancerSkillRepository;
import com.librework.modules.profile.infrastructure.mapper.FreelancerSkillJpaMapper;
import com.librework.modules.profile.infrastructure.repository.jpa.FreelancerSkillJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class FreelancerSkillRepositoryImpl implements FreelancerSkillRepository {
    private final FreelancerSkillJpaRepository jpaRepository;
    private final FreelancerSkillJpaMapper mapper;

    @Override
    public FreelancerSkill save(FreelancerSkill skill) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(skill)));
    }

    @Override
    public List<FreelancerSkill> findByFreelancerId(UUID freelancerId) {
        return jpaRepository.findByFreelancerId(freelancerId).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByFreelancerIdAndSkillId(UUID freelancerId, UUID skillId) {
        return jpaRepository.existsByFreelancerIdAndSkillId(freelancerId, skillId);
    }

    @Override
    public void deleteByFreelancerIdAndSkillId(UUID freelancerId, UUID skillId) {
        jpaRepository.deleteByFreelancerIdAndSkillId(freelancerId, skillId);
    }
}
