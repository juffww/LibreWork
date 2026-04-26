package com.librework.modules.profile.infrastructure.repository.jpa;

import com.librework.modules.profile.infrastructure.entity.FreelancerSkillJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FreelancerSkillJpaRepository extends JpaRepository<FreelancerSkillJpaEntity, UUID> {
    List<FreelancerSkillJpaEntity> findByFreelancerId(UUID freelancerId);
    boolean existsByFreelancerIdAndSkillId(UUID freelancerId, UUID skillId);
    void deleteByFreelancerIdAndSkillId(UUID freelancerId, UUID skillId);
}
