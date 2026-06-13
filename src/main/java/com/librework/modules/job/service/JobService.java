package com.librework.modules.job.service;

import com.librework.common.enums.BudgetType;
import com.librework.common.enums.ExperienceLevel;
import com.librework.common.enums.JobDuration;
import com.librework.common.response.PageResponse;
import com.librework.modules.job.dto.request.JobCreationRequest;
import com.librework.modules.job.dto.response.JobDetailResponse;
import com.librework.modules.job.dto.response.JobSummaryResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface JobService {
    JobDetailResponse create(JobCreationRequest request);

    JobDetailResponse getById(UUID id);

    List<JobSummaryResponse> getOpenJobs();

    List<JobSummaryResponse> getMyJobs();

    void close(UUID id);

    PageResponse<JobSummaryResponse> searchOpenJobs(
            String keyword,
            UUID categoryId,
            ExperienceLevel experienceLevel,
            BudgetType budgetType,
            JobDuration duration,
            int page,
            int size
    );
}
