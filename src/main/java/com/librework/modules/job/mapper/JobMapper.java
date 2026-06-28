package com.librework.modules.job.mapper;

import com.librework.modules.job.dto.response.JobDetailResponse;
import com.librework.modules.job.dto.response.JobSkillResponse;
import com.librework.modules.job.dto.response.JobSummaryResponse;
import com.librework.modules.job.entity.Job;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JobMapper {
    public JobSummaryResponse toSummaryResponse(Job job) {
        return toSummaryResponse(job, List.of());
    }

    public JobSummaryResponse toSummaryResponse(Job job, List<JobSkillResponse> skills) {
        return JobSummaryResponse.builder()
                .id(job.getId())
                .clientId(job.getClientId())
                .title(job.getTitle())
                .description(job.getDescription())
                .categoryId(job.getCategoryId())
                .budgetType(job.getBudgetType())
                .budgetFixed(job.getBudgetFixed())
                .budgetMin(job.getBudgetMin())
                .budgetMax(job.getBudgetMax())
                .currency(job.getCurrency())
                .duration(job.getDuration())
                .experienceLevel(job.getExperienceLevel())
                .status(job.getStatus())
                .proposalsCount(job.getProposalsCount())
                .visibility(job.getVisibility())
                .isUrgent(job.isUrgent())
                .freelancersNeeded(job.getFreelancersNeeded())
                .subcategoryId(job.getSubcategoryId())
                .skills(skills)
                .createdAt(job.getCreatedAt())
                .build();
    }

    public JobDetailResponse toDetailResponse(Job job, List<JobSkillResponse> skills) {
        return JobDetailResponse.builder()
                .id(job.getId())
                .clientId(job.getClientId())
                .categoryId(job.getCategoryId())
                .title(job.getTitle())
                .description(job.getDescription())
                .budgetType(job.getBudgetType())
                .budgetFixed(job.getBudgetFixed())
                .budgetMin(job.getBudgetMin())
                .budgetMax(job.getBudgetMax())
                .currency(job.getCurrency())
                .duration(job.getDuration())
                .experienceLevel(job.getExperienceLevel())
                .status(job.getStatus())
                .proposalsCount(job.getProposalsCount())
                .visibility(job.getVisibility())
                .isUrgent(job.isUrgent())
                .freelancersNeeded(job.getFreelancersNeeded())
                .subcategoryId(job.getSubcategoryId())
                .skills(skills)
                .createdAt(job.getCreatedAt())
                .updatedAt(job.getUpdatedAt())
                .build();
    }
}
