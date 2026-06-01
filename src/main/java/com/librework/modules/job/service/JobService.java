package com.librework.modules.job.service;

import com.librework.modules.job.dto.request.JobCreationRequest;
import com.librework.modules.job.dto.response.JobDetailResponse;
import com.librework.modules.job.dto.response.JobSummaryResponse;

import java.util.List;
import java.util.UUID;

public interface JobService {
    JobDetailResponse create(JobCreationRequest request);

    JobDetailResponse getById(UUID id);

    List<JobSummaryResponse> getOpenJobs();

    List<JobSummaryResponse> getMyJobs();

    void close(UUID id);
}
