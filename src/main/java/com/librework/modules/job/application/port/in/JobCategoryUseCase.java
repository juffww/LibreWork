package com.librework.modules.job.application.port.in;

import com.librework.modules.job.application.dto.request.JobCategoryRequest;
import com.librework.modules.job.application.dto.response.JobCategoryResponse;

import java.util.List;
import java.util.UUID;

public interface JobCategoryUseCase {
    List<JobCategoryResponse> getRoots();
    List<JobCategoryResponse> getChildren(UUID parentId);
    JobCategoryResponse getById(UUID id);
    JobCategoryResponse create(JobCategoryRequest request);
    JobCategoryResponse update(UUID id, JobCategoryRequest request);
    void delete(UUID id);
}