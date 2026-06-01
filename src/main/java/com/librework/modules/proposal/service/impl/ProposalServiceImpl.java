package com.librework.modules.proposal.service.impl;

import com.librework.modules.proposal.service.ProposalService;



import com.librework.common.enums.JobStatus;
import com.librework.common.enums.ProfileType;
import com.librework.common.enums.ProposalStatus;
import com.librework.exception.AppException;
import com.librework.exception.ErrorCode;
import com.librework.modules.identity.service.ActiveProfileService;
import com.librework.modules.identity.service.CurrentUserService;
import com.librework.modules.profile.service.ProfileLookupService;
import com.librework.modules.proposal.dto.request.SubmitProposalRequest;
import com.librework.modules.proposal.dto.response.ProposalResponse;
import com.librework.modules.proposal.mapper.ProposalMapper;
import com.librework.modules.job.entity.Job;
import com.librework.modules.proposal.entity.Proposal;
import com.librework.modules.job.repository.JobRepository;
import com.librework.modules.proposal.repository.ProposalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProposalServiceImpl implements ProposalService {

    private final ProposalRepository proposalRepository;
    private final JobRepository jobRepository;
    private final CurrentUserService currentUserService;
    private final ProfileLookupService profileLookupService;
    private final ActiveProfileService activeProfileService;
    private final ProposalMapper mapper;

    public ProposalResponse submit(UUID jobId, SubmitProposalRequest request) {
        UUID currentUserId = currentUserService.getCurrentUserId();
        requireActiveProfile(currentUserId, ProfileType.FREELANCER);
        UUID freelancerId = profileLookupService.getFreelancerProfileIdByUserId(currentUserId);

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

    public List<ProposalResponse> getByJob(UUID jobId) {
        // chỉ client sở hữu job mới xem được
        UUID currentUserId = currentUserService.getCurrentUserId();
        requireActiveProfile(currentUserId, ProfileType.CLIENT);
        UUID clientId = profileLookupService.getClientProfileIdByUserId(currentUserId);

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException());

        if (!job.isOwner(clientId)) {
            throw new RuntimeException();
        }

        return proposalRepository.findByJobId(jobId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    public List<ProposalResponse> getMyProposals() {
        UUID currentUserId = currentUserService.getCurrentUserId();
        requireActiveProfile(currentUserId, ProfileType.FREELANCER);
        UUID freelancerId = profileLookupService.getFreelancerProfileIdByUserId(currentUserId);
        return proposalRepository.findByFreelancerId(freelancerId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    public void accept(UUID proposalId) {
        UUID currentUserId = currentUserService.getCurrentUserId();
        requireActiveProfile(currentUserId, ProfileType.CLIENT);
        UUID clientId = profileLookupService.getClientProfileIdByUserId(currentUserId);

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

    public void reject(UUID proposalId) {
        UUID currentUserId = currentUserService.getCurrentUserId();
        requireActiveProfile(currentUserId, ProfileType.CLIENT);
        UUID clientId = profileLookupService.getClientProfileIdByUserId(currentUserId);

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

    public void withdraw(UUID proposalId) {
        UUID currentUserId = currentUserService.getCurrentUserId();
        requireActiveProfile(currentUserId, ProfileType.FREELANCER);
        UUID freelancerId = profileLookupService.getFreelancerProfileIdByUserId(currentUserId);

        Proposal proposal = proposalRepository.findById(proposalId)
                .orElseThrow(() -> new RuntimeException());

        if (!proposal.isOwner(freelancerId)) {
            throw new RuntimeException();
        }

        proposal.withdraw();
        proposalRepository.save(proposal);
    }

    private void requireActiveProfile(UUID userId, ProfileType expectedProfile) {
        if (activeProfileService.getActiveProfileType(userId) != expectedProfile) {
            throw new AppException(ErrorCode.FORBIDDEN);
        }
    }
}
