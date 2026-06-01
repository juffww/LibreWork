package com.librework.modules.job.repository;

import com.librework.modules.job.entity.JobCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JobCategoryRepository extends JpaRepository<JobCategory, UUID> {
    List<JobCategory> findByParentId(UUID parentId);
    List<JobCategory> findByParentIdIsNull();
    boolean existsBySlug(String slug);
}
