package com.librework.modules.job.repository;

import com.librework.common.enums.JobStatus;
import com.librework.modules.job.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JobRepository extends JpaRepository<Job, UUID> {
    List<Job> findByClientId(UUID clientId);
    List<Job> findByStatus(JobStatus status);
}
