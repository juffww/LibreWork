package com.librework.modules.job.infrastructure.repository.jpa;

import com.librework.common.enums.JobStatus;
import com.librework.modules.job.infrastructure.entity.JobJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JobJpaRepository extends JpaRepository<JobJpaEntity, UUID> {
    List<JobJpaEntity> findByClientId(UUID clientId);
    List<JobJpaEntity> findByStatus(JobStatus status);
}
