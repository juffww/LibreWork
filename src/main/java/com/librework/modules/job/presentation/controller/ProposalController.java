package com.librework.modules.job.presentation.controller;

import com.librework.common.response.ApiResponse;
import com.librework.modules.job.application.dto.request.SubmitProposalRequest;
import com.librework.modules.job.application.dto.response.ProposalResponse;
import com.librework.modules.job.application.port.in.ProposalUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ProposalController {
    private final ProposalUseCase proposalUseCase;

    @PostMapping("/api/v1/jobs/{jobId}/proposals")
    public ApiResponse<ProposalResponse> submit(@PathVariable UUID jobId,
                                                @RequestBody @Valid SubmitProposalRequest request) {
        return ApiResponse.ok(proposalUseCase.submit(jobId, request));
    }

    @GetMapping("/api/v1/jobs/{jobId}/proposals")
    public ApiResponse<List<ProposalResponse>> getByJob(@PathVariable UUID jobId) {
        return ApiResponse.ok(proposalUseCase.getByJob(jobId));
    }

    @GetMapping("/api/v1/proposals/me")
    public ApiResponse<List<ProposalResponse>> getMyProposals() {
        return ApiResponse.ok(proposalUseCase.getMyProposals());
    }

    @PatchMapping("/api/v1/proposals/{id}/accept")
    public ApiResponse<Void> accept(@PathVariable UUID id) {
        proposalUseCase.accept(id);
        return ApiResponse.ok(null);
    }

    @PatchMapping("/api/v1/proposals/{id}/reject")
    public ApiResponse<Void> reject(@PathVariable UUID id) {
        proposalUseCase.reject(id);
        return ApiResponse.ok(null);
    }

    @PatchMapping("/api/v1/proposals/{id}/withdraw")
    public ApiResponse<Void> withdraw(@PathVariable UUID id) {
        proposalUseCase.withdraw(id);
        return ApiResponse.ok(null);
    }
}
