package com.librework.modules.job.repository;

import com.librework.modules.job.entity.JobSkill;
import com.librework.modules.job.entity.JobSkillId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JobSkillRepository extends JpaRepository<JobSkill, JobSkillId> {
    List<JobSkill> findByJobId(UUID jobId);
    List<JobSkill> findByJobIdIn(List<UUID> jobIds);
    void deleteByJobId(UUID jobId);
}
