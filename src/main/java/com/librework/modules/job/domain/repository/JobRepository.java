package com.librework.modules.job.domain.repository;

import com.librework.modules.job.domain.entity.Job;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JobRepository {
    Job save(Job job);
    Optional<Job> findById(UUID id);
    List<Job> findByClientId(UUID clientId);
    List<Job> findOpenJobs();
    void deleteById(UUID id);
}