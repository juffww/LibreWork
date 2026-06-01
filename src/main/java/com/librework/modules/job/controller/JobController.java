package com.librework.modules.job.controller;

import com.librework.common.response.ApiResponse;
import com.librework.modules.job.dto.request.JobCreationRequest;
import com.librework.modules.job.dto.response.JobDetailResponse;
import com.librework.modules.job.dto.response.JobSummaryResponse;
import com.librework.modules.job.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
@Tag(name = "Jobs", description = "Job management (Client post jobs)")
public class JobController {
    private final JobService jobService;

    @GetMapping
    @Operation(summary = "Get list of open jobs", description = "Display open jobs for freelancers")
    public ApiResponse<List<JobSummaryResponse>> getOpenJobs() {
        return ApiResponse.ok(jobService.getOpenJobs());
    }

    @GetMapping("/me")
    @Operation(summary = "Get my jobs", description = "Get jobs posted by the current client")
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<List<JobSummaryResponse>> getMyJobs() {
        return ApiResponse.ok(jobService.getMyJobs());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get job details", description = "View details of a job by ID")
    public ApiResponse<JobDetailResponse> getById(@PathVariable UUID id) {
        return ApiResponse.ok(jobService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Create new job", description = "Client creates a new job")
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<JobDetailResponse> create(@RequestBody @Valid JobCreationRequest request) {
        return ApiResponse.ok(jobService.create(request));
    }

    @PatchMapping("/{id}/close")
    @Operation(summary = "Close job", description = "Change job status to Closed")
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<Void> close(@PathVariable UUID id) {
        jobService.close(id);
        return ApiResponse.ok(null);
    }
}
