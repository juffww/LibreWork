package com.librework.modules.profile.application.mapper;

import com.librework.modules.profile.application.dto.request.ClientProfileCreationRequest;
import com.librework.modules.profile.application.dto.request.ClientProfileUpdateRequest;
import com.librework.modules.profile.application.dto.request.FreelancerProfileUpdateRequest;
import com.librework.modules.profile.application.dto.response.ClientProfileResponse;
import com.librework.modules.profile.application.dto.response.FreelancerFileResponse;
import com.librework.modules.profile.domain.entity.ClientProfile;
import com.librework.modules.profile.domain.entity.FreelancerProfile;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProfileMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ClientProfile toClientProfile(ClientProfileCreationRequest request);

    ClientProfileResponse toClientProfileResponse(ClientProfile profile);

    FreelancerFileResponse toFreelancerProfileReponse(FreelancerProfile profile);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFreelancerProfile(@MappingTarget FreelancerProfile entity,
                                 FreelancerProfileUpdateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateClientProfile(@MappingTarget ClientProfile entity,
                             ClientProfileUpdateRequest request);
}
