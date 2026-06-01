package com.librework.modules.proposal.repository;

import com.librework.modules.proposal.entity.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProposalRepository extends JpaRepository<Proposal, UUID> {
    List<Proposal> findByJobId(UUID jobId);
    List<Proposal> findByFreelancerId(UUID freelancerId);
    boolean existsByJobIdAndFreelancerId(UUID jobId, UUID freelancerId);
}
