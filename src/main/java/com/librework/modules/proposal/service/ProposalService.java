package com.librework.modules.proposal.service;

import com.librework.modules.proposal.dto.request.SubmitProposalRequest;
import com.librework.modules.proposal.dto.response.ProposalResponse;

import java.util.List;
import java.util.UUID;

public interface ProposalService {
    ProposalResponse submit(UUID jobId, SubmitProposalRequest request);

    List<ProposalResponse> getByJob(UUID jobId);

    List<ProposalResponse> getMyProposals();

    void accept(UUID proposalId);

    void reject(UUID proposalId);

    void withdraw(UUID proposalId);
}
