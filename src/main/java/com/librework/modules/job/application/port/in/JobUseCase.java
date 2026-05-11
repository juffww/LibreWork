package com.librework.modules.job.application.port.in;

import com.librework.modules.job.application.dto.request.JobCreationRequest;
import com.librework.modules.job.application.dto.response.JobDetailResponse;
import com.librework.modules.job.application.dto.response.JobSummaryResponse;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface JobUseCase {
    JobDetailResponse create(@Valid JobCreationRequest request);
    JobDetailResponse getById(UUID id);
    List<JobSummaryResponse> getOpenJobs();
    List<JobSummaryResponse> getMyJobs();       // client xem job của mình
    void close(UUID id);                        // client đóng job
}