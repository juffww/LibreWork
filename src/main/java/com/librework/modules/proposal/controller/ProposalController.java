package com.librework.modules.proposal.controller;

import com.librework.common.response.ApiResponse;
import com.librework.modules.proposal.dto.request.SubmitProposalRequest;
import com.librework.modules.proposal.dto.response.ProposalResponse;
import com.librework.modules.proposal.service.ProposalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "Proposals", description = "Proposal management (Freelancer applications)")
public class ProposalController {
    private final ProposalService proposalService;

    @PostMapping("/api/v1/jobs/{jobId}/proposals")
    @Operation(summary = "Submit Proposal", description = "Freelancer submits a proposal for a job")
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<ProposalResponse> submit(@PathVariable UUID jobId,
                                                @RequestBody @Valid SubmitProposalRequest request) {
        return ApiResponse.ok(proposalService.submit(jobId, request));
    }

    @GetMapping("/api/v1/jobs/{jobId}/proposals")
    @Operation(summary = "Get proposals by Job", description = "Client views proposals for their job")
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<List<ProposalResponse>> getByJob(@PathVariable UUID jobId) {
        return ApiResponse.ok(proposalService.getByJob(jobId));
    }

    @GetMapping("/api/v1/proposals/me")
    @Operation(summary = "Get my proposals", description = "Freelancer views their submitted proposals")
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<List<ProposalResponse>> getMyProposals() {
        return ApiResponse.ok(proposalService.getMyProposals());
    }

    @PatchMapping("/api/v1/proposals/{id}/accept")
    @Operation(summary = "Accept proposal", description = "Client accepts a proposal (Hire freelancer)")
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<Void> accept(@PathVariable UUID id) {
        proposalService.accept(id);
        return ApiResponse.ok(null);
    }

    @PatchMapping("/api/v1/proposals/{id}/reject")
    @Operation(summary = "Reject proposal", description = "Client rejects a proposal")
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<Void> reject(@PathVariable UUID id) {
        proposalService.reject(id);
        return ApiResponse.ok(null);
    }

    @PatchMapping("/api/v1/proposals/{id}/withdraw")
    @Operation(summary = "Withdraw proposal", description = "Freelancer withdraws a submitted proposal")
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<Void> withdraw(@PathVariable UUID id) {
        proposalService.withdraw(id);
        return ApiResponse.ok(null);
    }
}
