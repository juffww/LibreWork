package com.librework.modules.job.infrastructure.repository.jpa;

import com.librework.modules.job.infrastructure.entity.JobSkillId;
import com.librework.modules.job.infrastructure.entity.JobSkillJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JobSkillJpaRepository extends JpaRepository<JobSkillJpaEntity, JobSkillId> {
    List<JobSkillJpaEntity> findByJobId(UUID jobId);
    void deleteByJobId(UUID jobId);
}
