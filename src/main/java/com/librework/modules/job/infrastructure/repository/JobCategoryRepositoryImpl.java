package com.librework.modules.job.infrastructure.repository;

import com.librework.modules.job.domain.entity.JobCategory;
import com.librework.modules.job.domain.repository.JobCategoryRepository;
import com.librework.modules.job.infrastructure.entity.JobCategoryJpaEntity;
import com.librework.modules.job.infrastructure.repository.jpa.JobCategoryJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class JobCategoryRepositoryImpl implements JobCategoryRepository {

    private final JobCategoryJpaRepository jpaRepository;

    @Override
    public JobCategory save(JobCategory category) {
        JobCategoryJpaEntity entity = toEntity(category);
        return toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<JobCategory> findById(UUID id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<JobCategory> findAll() {
        return jpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public List<JobCategory> findByParentId(UUID parentId) {
        return jpaRepository.findByParentId(parentId).stream().map(this::toDomain).toList();
    }

    @Override
    public List<JobCategory> findRoots() {
        return jpaRepository.findByParentIdIsNull().stream().map(this::toDomain).toList();
    }

    @Override
    public boolean existsBySlug(String slug) {
        return jpaRepository.existsBySlug(slug);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    private JobCategory toDomain(JobCategoryJpaEntity entity) {
        return JobCategory.builder()
                .id(entity.getId())
                .parentId(entity.getParentId())
                .name(entity.getName())
                .slug(entity.getSlug())
                .iconUrl(entity.getIconUrl())
                .description(entity.getDescription())
                .sortOrder(entity.getSortOrder())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    private JobCategoryJpaEntity toEntity(JobCategory domain) {
        return JobCategoryJpaEntity.builder()
                .id(domain.getId())
                .parentId(domain.getParentId())
                .name(domain.getName())
                .slug(domain.getSlug())
                .iconUrl(domain.getIconUrl())
                .description(domain.getDescription())
                .sortOrder(domain.getSortOrder())
                .createdAt(domain.getCreatedAt())
                .build();
    }
}

