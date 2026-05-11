package com.librework.modules.job.infrastructure.repository.jpa;

import com.librework.modules.job.infrastructure.entity.JobCategoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JobCategoryJpaRepository extends JpaRepository<JobCategoryJpaEntity, UUID> {
    List<JobCategoryJpaEntity> findByParentIdIsNull();
    List<JobCategoryJpaEntity> findByParentId(UUID parentId);
    boolean existsBySlug(String slug);
}
