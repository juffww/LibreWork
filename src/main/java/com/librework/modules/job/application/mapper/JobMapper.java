package com.librework.modules.job.application.mapper;

import com.librework.modules.job.application.dto.response.JobDetailResponse;
import com.librework.modules.job.application.dto.response.JobSkillResponse;
import com.librework.modules.job.application.dto.response.JobSummaryResponse;
import com.librework.modules.job.domain.entity.Job;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface JobMapper {
    JobSummaryResponse toSummaryResponse(Job job);

    @Mapping(target = "skills", source = "skills")
    JobDetailResponse toDetailResponse(Job job, List<JobSkillResponse> skills);
}