package com.librework.modules.profile.domain.repository;

import com.librework.modules.profile.domain.entity.FreelancerSkill;

import java.util.List;
import java.util.UUID;

public interface FreelancerSkillRepository {
    FreelancerSkill save(FreelancerSkill skill);
    List<FreelancerSkill> findByFreelancerId(UUID freelancerId);
    boolean existsByFreelancerIdAndSkillId(UUID freelancerId, UUID skillId);
    void deleteByFreelancerIdAndSkillId(UUID freelancerId, UUID skillId);
}
