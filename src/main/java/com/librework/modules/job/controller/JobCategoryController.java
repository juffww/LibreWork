package com.librework.modules.job.controller;

import com.librework.common.response.ApiResponse;
import com.librework.modules.job.dto.request.JobCategoryRequest;
import com.librework.modules.job.dto.request.JobCreationRequest;
import com.librework.modules.proposal.dto.request.SubmitProposalRequest;
import com.librework.modules.job.dto.response.JobCategoryResponse;
import com.librework.modules.job.dto.response.JobDetailResponse;
import com.librework.modules.job.dto.response.JobSummaryResponse;
import com.librework.modules.proposal.dto.response.ProposalResponse;
import com.librework.modules.job.service.JobCategoryService;
import com.librework.modules.job.service.JobService;
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
@RequestMapping("/api/v1/job-categories")
@RequiredArgsConstructor
@Tag(name = "Job Categories", description = "Job category management")
public class JobCategoryController {
    private final JobCategoryService jobCategoryService;

    @GetMapping
    @Operation(summary = "Get root categories", description = "Get list of top-level categories")
    public ApiResponse<List<JobCategoryResponse>> getRoots() {
        return ApiResponse.ok(jobCategoryService.getRoots());
    }

    @GetMapping("/{id}/children")
    @Operation(summary = "Get child categories", description = "Get list of child categories by parent ID")
    public ApiResponse<List<JobCategoryResponse>> getChildren(@PathVariable UUID id) {
        return ApiResponse.ok(jobCategoryService.getChildren(id));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Category details")
    public ApiResponse<JobCategoryResponse> getById(@PathVariable UUID id) {
        return ApiResponse.ok(jobCategoryService.getById(id));
    }

    @PostMapping           // ADMIN
    @Operation(summary = "Create category", description = "Create a category (Requires ADMIN role)")
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<JobCategoryResponse> create(@RequestBody @Valid JobCategoryRequest request) {
        return ApiResponse.ok(jobCategoryService.create(request));
    }

    @PutMapping("/{id}")   // ADMIN
    @Operation(summary = "Update category", description = "Update category info (Requires ADMIN role)")
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<JobCategoryResponse> update(@PathVariable UUID id,
                                                   @RequestBody @Valid JobCategoryRequest request) {
        return ApiResponse.ok(jobCategoryService.update(id, request));
    }

    @DeleteMapping("/{id}") // ADMIN
    @Operation(summary = "Delete category", description = "Delete a category by ID (Requires ADMIN role)")
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<Void> delete(@PathVariable UUID id) {
        jobCategoryService.delete(id);
        return ApiResponse.ok(null);
    }
}
