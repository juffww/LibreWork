package com.librework.modules.profile.repository;

import com.librework.modules.profile.entity.FreelancerSkill;
import com.librework.modules.profile.entity.FreelancerSkillId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FreelancerSkillRepository extends JpaRepository<FreelancerSkill, FreelancerSkillId> {
    List<FreelancerSkill> findByFreelancerId(UUID freelancerId);
    boolean existsByFreelancerIdAndSkillId(UUID freelancerId, UUID skillId);
    void deleteByFreelancerIdAndSkillId(UUID freelancerId, UUID skillId);
}
