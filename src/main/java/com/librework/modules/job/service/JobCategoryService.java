package com.librework.modules.job.service;

import com.librework.modules.job.dto.request.JobCategoryRequest;
import com.librework.modules.job.dto.response.JobCategoryResponse;

import java.util.List;
import java.util.UUID;

public interface JobCategoryService {
    List<JobCategoryResponse> getRoots();

    List<JobCategoryResponse> getChildren(UUID parentId);

    JobCategoryResponse getById(UUID id);

    JobCategoryResponse create(JobCategoryRequest request);

    JobCategoryResponse update(UUID id, JobCategoryRequest request);

    void delete(UUID id);
}
