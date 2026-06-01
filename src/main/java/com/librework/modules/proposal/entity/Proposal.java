package com.librework.modules.proposal.entity;

import com.librework.common.enums.BudgetType;
import com.librework.common.enums.ProposalStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "proposals")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Proposal {
    @Id
    private UUID id;

    @Column(name = "job_id", nullable = false)
    private UUID jobId;

    @Column(name = "freelancer_id", nullable = false)
    private UUID freelancerId;

    @Column(name = "cover_letter", nullable = false, columnDefinition = "text")
    private String coverLetter;

    @Enumerated(EnumType.STRING)
    @Column(name = "bid_type", nullable = false, length = 10)
    private BudgetType bidType;

    @Column(name = "bid_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal bidAmount;

    @Column(name = "bid_duration", nullable = false)
    private int bidDuration;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ProposalStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

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
