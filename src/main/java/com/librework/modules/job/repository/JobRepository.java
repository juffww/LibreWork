package com.librework.modules.job.repository;

import com.librework.common.enums.BudgetType;
import com.librework.common.enums.ExperienceLevel;
import com.librework.common.enums.JobDuration;
import com.librework.common.enums.JobStatus;
import com.librework.modules.job.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JobRepository extends JpaRepository<Job, UUID> {
    List<Job> findByClientId(UUID clientId);

    List<Job> findByStatus(JobStatus status);

    // Nếu keyword là null -> true -> bỏ qua search true
    @Query(
            """
            select j
            from Job j
            where j.status = JobStatus.OPEN
                 and (:keywordPattern = ''
                             or lower(j.title) like :keywordPattern
                             or lower(j.description) like :keywordPattern)
                 and (:categoryId is null
                             or j.categoryId = :categoryId
                             or j.subcategoryId = :categoryId)
                 and (:level is null
                             or j.experienceLevel = :level)
                 and (:budgetType is null
                             or j.budgetType = :budgetType)
                 and (:duration is null
                             or j.duration = :duration)
            """
    )
    Page<Job> searchOpenJobs(
            @Param("keywordPattern") String keywordPattern,
            @Param("categoryId") UUID categoryId,
            @Param("level") ExperienceLevel level,
            @Param("budgetType")BudgetType budgetType,
            @Param("duration") JobDuration duration,
            Pageable pageable
            );
}
