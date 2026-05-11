package com.librework.modules.job.domain.repository;

import com.librework.modules.job.domain.entity.JobCategory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JobCategoryRepository {
    JobCategory save(JobCategory category);
    Optional<JobCategory> findById(UUID id);
    List<JobCategory> findAll();
    List<JobCategory> findByParentId(UUID parentId);  // subcategories
    List<JobCategory> findRoots();                     // parentId = null
    boolean existsBySlug(String slug);
    void deleteById(UUID id);
}