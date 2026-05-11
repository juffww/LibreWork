package com.librework.modules.job.infrastructure.repository;

import com.librework.common.enums.JobStatus;
import com.librework.modules.job.domain.entity.Job;
import com.librework.modules.job.domain.entity.JobSkill;
import com.librework.modules.job.domain.repository.JobRepository;
import com.librework.modules.job.infrastructure.entity.JobJpaEntity;
import com.librework.modules.job.infrastructure.entity.JobSkillJpaEntity;
import com.librework.modules.job.infrastructure.repository.jpa.JobJpaRepository;
import com.librework.modules.job.infrastructure.repository.jpa.JobSkillJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class JobRepositoryImpl implements JobRepository {

    private final JobJpaRepository jobJpaRepository;
    private final JobSkillJpaRepository jobSkillJpaRepository;

    @Override
    public Job save(Job job) {
        JobJpaEntity entity = toEntity(job);
        jobJpaRepository.save(entity);

        // sync skills: xóa cũ, insert mới
        jobSkillJpaRepository.deleteByJobId(job.getId());
        List<JobSkillJpaEntity> skillEntities = job.getSkills().stream()
                .map(s -> JobSkillJpaEntity.builder()
                        .jobId(s.getJobId())
                        .skillId(s.getSkillId())
                        .required(s.isRequired())
                        .build())
                .toList();
        jobSkillJpaRepository.saveAll(skillEntities);

        return toDomain(entity, job.getSkills());
    }

    @Override
    public Optional<Job> findById(UUID id) {
        return jobJpaRepository.findById(id).map(entity -> {
            List<JobSkill> skills = jobSkillJpaRepository.findByJobId(id).stream()
                    .map(s -> JobSkill.builder()
                            .jobId(s.getJobId())
                            .skillId(s.getSkillId())
                            .required(s.isRequired())
                            .build())
                    .toList();
            return toDomain(entity, skills);
        });
    }

    @Override
    public List<Job> findByClientId(UUID clientId) {
        return jobJpaRepository.findByClientId(clientId).stream()
                .map(e -> toDomain(e, List.of()))  // summary không cần skills
                .toList();
    }

    @Override
    public List<Job> findOpenJobs() {
        return jobJpaRepository.findByStatus(JobStatus.OPEN).stream()
                .map(e -> toDomain(e, List.of()))
                .toList();
    }

    @Override
    public void deleteById(UUID id) {
        jobSkillJpaRepository.deleteByJobId(id);
        jobJpaRepository.deleteById(id);
    }

    private Job toDomain(JobJpaEntity e, List<JobSkill> skills) {
        return Job.builder()
                .id(e.getId())
                .clientId(e.getClientId())
                .categoryId(e.getCategoryId())
                .title(e.getTitle())
                .description(e.getDescription())
                .jobType(e.getJobType())
                .budgetType(e.getBudgetType())
                .budgetFixed(e.getBudgetFixed())
                .budgetMin(e.getBudgetMin())
                .budgetMax(e.getBudgetMax())
                .duration(e.getDuration())
                .experienceLevel(e.getExperienceLevel())
                .status(e.getStatus())
                .proposalsCount(e.getProposalsCount())
                .createdAt(e.getCreatedAt())
                .updatedAt(e.getUpdatedAt())
                .skills(new ArrayList<>(skills))
                .build();
    }

    private JobJpaEntity toEntity(Job job) {
        return JobJpaEntity.builder()
                .id(job.getId())
                .clientId(job.getClientId())
                .categoryId(job.getCategoryId())
                .title(job.getTitle())
                .description(job.getDescription())
                .jobType(job.getJobType())
                .budgetType(job.getBudgetType())
                .budgetFixed(job.getBudgetFixed())
                .budgetMin(job.getBudgetMin())
                .budgetMax(job.getBudgetMax())
                .duration(job.getDuration())
                .experienceLevel(job.getExperienceLevel())
                .status(job.getStatus())
                .proposalsCount(job.getProposalsCount())
                .createdAt(job.getCreatedAt())
                .updatedAt(job.getUpdatedAt())
                .build();
    }
}