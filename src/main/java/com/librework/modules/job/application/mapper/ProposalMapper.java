package com.librework.modules.job.application.mapper;

import com.librework.modules.job.application.dto.response.ProposalResponse;
import com.librework.modules.job.domain.entity.Proposal;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProposalMapper {
    ProposalResponse toResponse(Proposal proposal);
}