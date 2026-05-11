package com.librework.modules.job.infrastructure.repository.jpa;

import com.librework.modules.job.infrastructure.entity.ProposalJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProposalJpaRepository extends JpaRepository<ProposalJpaEntity, UUID> {
    List<ProposalJpaEntity> findByJobId(UUID jobId);
    List<ProposalJpaEntity> findByFreelancerId(UUID freelancerId);
    boolean existsByJobIdAndFreelancerId(UUID jobId, UUID freelancerId);
}