package com.librework.modules.job.presentation.controller;

import com.librework.common.response.ApiResponse;
import com.librework.modules.job.application.dto.request.JobCategoryRequest;
import com.librework.modules.job.application.dto.request.JobCreationRequest;
import com.librework.modules.job.application.dto.request.SubmitProposalRequest;
import com.librework.modules.job.application.dto.response.JobCategoryResponse;
import com.librework.modules.job.application.dto.response.JobDetailResponse;
import com.librework.modules.job.application.dto.response.JobSummaryResponse;
import com.librework.modules.job.application.dto.response.ProposalResponse;
import com.librework.modules.job.application.port.in.JobCategoryUseCase;
import com.librework.modules.job.application.port.in.JobUseCase;
import com.librework.modules.job.application.port.in.ProposalUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/job-categories")
@RequiredArgsConstructor
public class JobCategoryController {
    private final JobCategoryUseCase jobCategoryUseCase;

    @GetMapping
    public ApiResponse<List<JobCategoryResponse>> getRoots() {
        return ApiResponse.ok(jobCategoryUseCase.getRoots());
    }

    @GetMapping("/{id}/children")
    public ApiResponse<List<JobCategoryResponse>> getChildren(@PathVariable UUID id) {
        return ApiResponse.ok(jobCategoryUseCase.getChildren(id));
    }

    @GetMapping("/{id}")
    public ApiResponse<JobCategoryResponse> getById(@PathVariable UUID id) {
        return ApiResponse.ok(jobCategoryUseCase.getById(id));
    }

    @PostMapping           // ADMIN
    public ApiResponse<JobCategoryResponse> create(@RequestBody @Valid JobCategoryRequest request) {
        return ApiResponse.ok(jobCategoryUseCase.create(request));
    }

    @PutMapping("/{id}")   // ADMIN
    public ApiResponse<JobCategoryResponse> update(@PathVariable UUID id,
                                                   @RequestBody @Valid JobCategoryRequest request) {
        return ApiResponse.ok(jobCategoryUseCase.update(id, request));
    }

    @DeleteMapping("/{id}") // ADMIN
    public ApiResponse<Void> delete(@PathVariable UUID id) {
        jobCategoryUseCase.delete(id);
        return ApiResponse.ok(null);
    }
}

