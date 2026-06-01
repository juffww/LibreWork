package com.librework.modules.job.mapper;

import com.librework.modules.job.dto.response.JobCategoryResponse;
import com.librework.modules.job.entity.JobCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JobCategoryMapper {
    JobCategoryResponse toResponse(JobCategory category);
}