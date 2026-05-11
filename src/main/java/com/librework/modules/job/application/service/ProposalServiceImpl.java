package com.librework.modules.job.application.service;

import com.librework.common.enums.JobStatus;
import com.librework.common.enums.ProposalStatus;
import com.librework.common.port.CurrentUserPort;
import com.librework.common.port.ClientProfileQueryPort;
import com.librework.common.port.FreelancerProfileQueryPort;
import com.librework.modules.job.application.dto.request.SubmitProposalRequest;
import com.librework.modules.job.application.dto.response.ProposalResponse;
import com.librework.modules.job.application.mapper.ProposalMapper;
import com.librework.modules.job.application.port.in.ProposalUseCase;
import com.librework.modules.job.domain.entity.Job;
import com.librework.modules.job.domain.entity.Proposal;
import com.librework.modules.job.domain.repository.JobRepository;
import com.librework.modules.job.domain.repository.ProposalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProposalServiceImpl implements ProposalUseCase {

    private final ProposalRepository proposalRepository;
    private final JobRepository jobRepository;
    private final CurrentUserPort currentUserPort;
    private final FreelancerProfileQueryPort freelancerProfileQueryPort;
    private final ClientProfileQueryPort clientProfileQueryPort;
    private final ProposalMapper mapper;

    @Override
    public ProposalResponse submit(UUID jobId, SubmitProposalRequest request) {
        UUID currentUserId = currentUserPort.getCurrentUserId();
        UUID freelancerId = freelancerProfileQueryPort.findIdByUserId(currentUserId);

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException());

        if (job.getStatus() != JobStatus.OPEN) {
            throw new RuntimeException();
        }

        if (proposalRepository.existsByJobIdAndFreelancerId(jobId, freelancerId)) {
            throw new RuntimeException();
        }

        Proposal proposal = Proposal.builder()
                .id(UUID.randomUUID())
                .jobId(jobId)
                .freelancerId(freelancerId)
                .coverLetter(request.getCoverLetter())
                .bidType(request.getBidType())
                .bidAmount(request.getBidAmount())
                .bidDuration(request.getBidDuration())
                .status(ProposalStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return mapper.toResponse(proposalRepository.save(proposal));
    }

    @Override
    public List<ProposalResponse> getByJob(UUID jobId) {
        // chỉ client sở hữu job mới xem được
        UUID currentUserId = currentUserPort.getCurrentUserId();
        UUID clientId = clientProfileQueryPort.findIdByUserId(currentUserId);

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException());

        if (!job.isOwner(clientId)) {
            throw new RuntimeException();
        }

        return proposalRepository.findByJobId(jobId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<ProposalResponse> getMyProposals() {
        UUID currentUserId = currentUserPort.getCurrentUserId();
        UUID freelancerId = freelancerProfileQueryPort.findIdByUserId(currentUserId);
        return proposalRepository.findByFreelancerId(freelancerId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void accept(UUID proposalId) {
        UUID currentUserId = currentUserPort.getCurrentUserId();
        UUID clientId = clientProfileQueryPort.findIdByUserId(currentUserId);

        Proposal proposal = proposalRepository.findById(proposalId)
                .orElseThrow(() -> new RuntimeException());

        Job job = jobRepository.findById(proposal.getJobId())
                .orElseThrow(() -> new RuntimeException());

        if (!job.isOwner(clientId)) {
            throw new RuntimeException();
        }

        proposal.accept();
        job.markInProgress();

        proposalRepository.save(proposal);
        jobRepository.save(job);
    }

    @Override
    public void reject(UUID proposalId) {
        UUID currentUserId = currentUserPort.getCurrentUserId();
        UUID clientId = clientProfileQueryPort.findIdByUserId(currentUserId);

        Proposal proposal = proposalRepository.findById(proposalId)
                .orElseThrow(() -> new RuntimeException());

        Job job = jobRepository.findById(proposal.getJobId())
                .orElseThrow(() -> new RuntimeException());

        if (!job.isOwner(clientId)) {
            throw new RuntimeException();
        }

        proposal.reject();
        proposalRepository.save(proposal);
    }

    @Override
    public void withdraw(UUID proposalId) {
        UUID currentUserId = currentUserPort.getCurrentUserId();
        UUID freelancerId = freelancerProfileQueryPort.findIdByUserId(currentUserId);

        Proposal proposal = proposalRepository.findById(proposalId)
                .orElseThrow(() -> new RuntimeException());

        if (!proposal.isOwner(freelancerId)) {
            throw new RuntimeException();
        }

        proposal.withdraw();
        proposalRepository.save(proposal);
    }
}