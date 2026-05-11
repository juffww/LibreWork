package com.librework.modules.job.presentation.controller;

import com.librework.common.response.ApiResponse;
import com.librework.modules.job.application.dto.request.JobCreationRequest;
import com.librework.modules.job.application.dto.response.JobDetailResponse;
import com.librework.modules.job.application.dto.response.JobSummaryResponse;
import com.librework.modules.job.application.port.in.JobUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
public class JobController {
    private final JobUseCase jobUseCase;

    @GetMapping
    public ApiResponse<List<JobSummaryResponse>> getOpenJobs() {
        return ApiResponse.ok(jobUseCase.getOpenJobs());
    }

    @GetMapping("/me")
    public ApiResponse<List<JobSummaryResponse>> getMyJobs() {
        return ApiResponse.ok(jobUseCase.getMyJobs());
    }

    @GetMapping("/{id}")
    public ApiResponse<JobDetailResponse> getById(@PathVariable UUID id) {
        return ApiResponse.ok(jobUseCase.getById(id));
    }

    @PostMapping
    public ApiResponse<JobDetailResponse> create(@RequestBody @Valid JobCreationRequest request) {
        return ApiResponse.ok(jobUseCase.create(request));
    }

    @PatchMapping("/{id}/close")
    public ApiResponse<Void> close(@PathVariable UUID id) {
        jobUseCase.close(id);
        return ApiResponse.ok(null);
    }
}
