package com.librework.modules.job.application.port.in;

import com.librework.modules.job.application.dto.request.SubmitProposalRequest;
import com.librework.modules.job.application.dto.response.ProposalResponse;

import java.util.List;
import java.util.UUID;

public interface ProposalUseCase {
    ProposalResponse submit(UUID jobId, SubmitProposalRequest request); // freelancer nộp
    List<ProposalResponse> getByJob(UUID jobId);                        // client xem proposals của job
    List<ProposalResponse> getMyProposals();                            // freelancer xem proposals của mình
    void accept(UUID proposalId);                                       // client accept
    void reject(UUID proposalId);                                       // client reject
    void withdraw(UUID proposalId);                                     // freelancer rút
}