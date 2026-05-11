package com.librework.modules.job.domain.entity;

import com.librework.common.enums.BudgetType;
import com.librework.common.enums.ProposalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class Proposal {
    private UUID id;
    private UUID jobId;
    private UUID freelancerId;      // FK → freelancer_profiles.id
    private String coverLetter;
    private BudgetType bidType;
    private BigDecimal bidAmount;
    private int bidDuration;        // số ngày hoàn thành
    private ProposalStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // --- business methods ---

    public void accept() {
        this.status = ProposalStatus.ACCEPTED;
        this.updatedAt = LocalDateTime.now();
    }

    public void reject() {
        this.status = ProposalStatus.REJECTED;
        this.updatedAt = LocalDateTime.now();
    }

    public void withdraw() {
        this.status = ProposalStatus.WITHDRAWN;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isOwner(UUID freelancerId) {
        return this.freelancerId.equals(freelancerId);
    }
}