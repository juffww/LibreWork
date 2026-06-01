package com.librework.modules.proposal.dto.request;

import com.librework.common.enums.BudgetType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class SubmitProposalRequest {
    @NotBlank
    private String coverLetter;

    @NotNull
    private BudgetType bidType;

    @NotNull
    @Positive
    private BigDecimal bidAmount;

    @Min(1)
    private int bidDuration;
}