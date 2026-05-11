package com.librework.modules.job.application.service;

import com.librework.common.exception.AppException;
import com.librework.common.exception.ErrorCode;
import com.librework.modules.job.application.dto.request.JobCategoryRequest;
import com.librework.modules.job.application.dto.response.JobCategoryResponse;
import com.librework.modules.job.application.mapper.JobCategoryMapper;
import com.librework.modules.job.application.port.in.JobCategoryUseCase;
import com.librework.modules.job.domain.entity.JobCategory;
import com.librework.modules.job.domain.repository.JobCategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class JobCategoryServiceImpl implements JobCategoryUseCase {

    private final JobCategoryRepository jobCategoryRepository;
    private final JobCategoryMapper mapper;

    @Override
    public List<JobCategoryResponse> getRoots() {
        return jobCategoryRepository.findRoots().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<JobCategoryResponse> getChildren(UUID parentId) {
        return jobCategoryRepository.findByParentId(parentId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public JobCategoryResponse getById(UUID id) {
        return jobCategoryRepository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException());
    }

    @Override
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

    @Override
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

    @Override
    public void delete(UUID id) {
        if (jobCategoryRepository.findById(id).isEmpty()) {
            throw new RuntimeException();
        }
        jobCategoryRepository.deleteById(id);
    }
}