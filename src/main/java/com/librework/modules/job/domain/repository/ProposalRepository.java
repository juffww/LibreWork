package com.librework.modules.job.domain.repository;

import com.librework.modules.job.domain.entity.Proposal;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProposalRepository {
    Proposal save(Proposal proposal);
    Optional<Proposal> findById(UUID id);
    List<Proposal> findByJobId(UUID jobId);
    List<Proposal> findByFreelancerId(UUID freelancerId);
    boolean existsByJobIdAndFreelancerId(UUID jobId, UUID freelancerId);
    void deleteById(UUID id);
}