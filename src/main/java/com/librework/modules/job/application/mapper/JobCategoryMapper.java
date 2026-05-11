package com.librework.modules.job.application.mapper;

import com.librework.modules.job.application.dto.response.JobCategoryResponse;
import com.librework.modules.job.domain.entity.JobCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JobCategoryMapper {
    JobCategoryResponse toResponse(JobCategory category);
}