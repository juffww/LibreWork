package com.librework.modules.proposal.dto.response;

import com.librework.common.enums.BudgetType;
import com.librework.common.enums.ProposalStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record ProposalResponse(
        UUID id,
        UUID jobId,
        UUID freelancerId,
        String coverLetter,
        BudgetType bidType,
        BigDecimal bidAmount,
        int bidDuration,
        ProposalStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}