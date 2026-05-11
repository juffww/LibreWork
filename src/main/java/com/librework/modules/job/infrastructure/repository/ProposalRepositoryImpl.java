package com.librework.modules.job.infrastructure.repository;

import com.librework.modules.job.domain.entity.Proposal;
import com.librework.modules.job.domain.repository.ProposalRepository;
import com.librework.modules.job.infrastructure.entity.ProposalJpaEntity;
import com.librework.modules.job.infrastructure.repository.jpa.ProposalJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class ProposalRepositoryImpl implements ProposalRepository {

    private final ProposalJpaRepository jpaRepository;

    @Override
    public Proposal save(Proposal proposal) {
        ProposalJpaEntity entity = toEntity(proposal);
        return toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<Proposal> findById(UUID id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Proposal> findByJobId(UUID jobId) {
        return jpaRepository.findByJobId(jobId).stream().map(this::toDomain).toList();
    }

    @Override
    public List<Proposal> findByFreelancerId(UUID freelancerId) {
        return jpaRepository.findByFreelancerId(freelancerId).stream().map(this::toDomain).toList();
    }

    @Override
    public boolean existsByJobIdAndFreelancerId(UUID jobId, UUID freelancerId) {
        return jpaRepository.existsByJobIdAndFreelancerId(jobId, freelancerId);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    private Proposal toDomain(ProposalJpaEntity entity) {
        return Proposal.builder()
                .id(entity.getId())
                .jobId(entity.getJobId())
                .freelancerId(entity.getFreelancerId())
                .coverLetter(entity.getCoverLetter())
                .bidType(entity.getBidType())
                .bidAmount(entity.getBidAmount())
                .bidDuration(entity.getBidDuration())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    private ProposalJpaEntity toEntity(Proposal domain) {
        return ProposalJpaEntity.builder()
                .id(domain.getId())
                .jobId(domain.getJobId())
                .freelancerId(domain.getFreelancerId())
                .coverLetter(domain.getCoverLetter())
                .bidType(domain.getBidType())
                .bidAmount(domain.getBidAmount())
                .bidDuration(domain.getBidDuration())
                .status(domain.getStatus())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}

