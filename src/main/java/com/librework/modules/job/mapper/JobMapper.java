package com.librework.modules.job.mapper;

import com.librework.modules.job.dto.response.JobDetailResponse;
import com.librework.modules.job.dto.response.JobSkillResponse;
import com.librework.modules.job.dto.response.JobSummaryResponse;
import com.librework.modules.job.entity.Job;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface JobMapper {
    @Mapping(target = "isUrgent", source = "urgent")
    JobSummaryResponse toSummaryResponse(Job job);

    @Mapping(target = "skills", source = "skills")
    @Mapping(target = "isUrgent", source = "job.urgent")
    JobDetailResponse toDetailResponse(Job job, List<JobSkillResponse> skills);
}