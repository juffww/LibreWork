package com.librework.modules.job.service.impl;

import com.librework.modules.job.service.JobCategoryService;



import com.librework.modules.job.dto.request.JobCategoryRequest;
import com.librework.modules.job.dto.response.JobCategoryResponse;
import com.librework.modules.job.mapper.JobCategoryMapper;
import com.librework.modules.job.entity.JobCategory;
import com.librework.modules.job.repository.JobCategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class JobCategoryServiceImpl implements JobCategoryService {

    private final JobCategoryRepository jobCategoryRepository;
    private final JobCategoryMapper mapper;

    public List<JobCategoryResponse> getRoots() {
        return jobCategoryRepository.findByParentIdIsNull().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public List<JobCategoryResponse> getChildren(UUID parentId) {
        return jobCategoryRepository.findByParentId(parentId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    public JobCategoryResponse getById(UUID id) {
        return jobCategoryRepository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException());
    }

    public JobCategoryResponse create(JobCategoryRequest request) {
        if (jobCategoryRepository.existsBySlug(request.getSlug())) {
            throw new RuntimeException();
        }

        JobCategory category = JobCategory.builder()
                .id(UUID.randomUUID())
                .parentId(request.getParentId())
                .name(request.getName())
                .slug(request.getSlug())
                .iconUrl(request.getIconUrl())
                .description(request.getDescription())
                .sortOrder(request.getSortOrder())
                .createdAt(LocalDateTime.now())
                .build();

        return mapper.toResponse(jobCategoryRepository.save(category));
    }

    public JobCategoryResponse update(UUID id, JobCategoryRequest request) {
        JobCategory category = jobCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());

        category.update(
                request.getName(),
                request.getSlug(),
                request.getIconUrl(),
                request.getDescription(),
                request.getSortOrder()
        );

        return mapper.toResponse(jobCategoryRepository.save(category));
    }

    public void delete(UUID id) {
        if (jobCategoryRepository.findById(id).isEmpty()) {
            throw new RuntimeException();
        }
        jobCategoryRepository.deleteById(id);
    }
}
