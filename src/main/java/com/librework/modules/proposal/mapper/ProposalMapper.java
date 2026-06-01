package com.librework.modules.proposal.mapper;

import com.librework.modules.proposal.dto.response.ProposalResponse;
import com.librework.modules.proposal.entity.Proposal;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProposalMapper {
    ProposalResponse toResponse(Proposal proposal);
}