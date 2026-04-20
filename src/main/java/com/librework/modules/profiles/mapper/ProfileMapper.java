package com.librework.modules.profiles.mapper;

import com.librework.modules.profiles.dto.request.ClientProfileCreationRequest;
import com.librework.modules.profiles.dto.request.ClientProfileUpdateRequest;
import com.librework.modules.profiles.dto.request.FreelancerProfileUpdateRequest;
import com.librework.modules.profiles.dto.response.ClientProfileResponse;
import com.librework.modules.profiles.dto.response.FreelancerReponse;
import com.librework.modules.profiles.dto.response.ProfileResponse;
import com.librework.modules.profiles.entity.ClientProfile;
import com.librework.modules.profiles.entity.FreelancerProfile;
import com.librework.common.ProfileType;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProfileMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ClientProfile toClientProfile(ClientProfileCreationRequest request);

    ClientProfileResponse toClientProfileResponse(ClientProfile profile);

    FreelancerReponse toFreelancerProfileReponse(FreelancerProfile profile);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFreelancerProfile(@MappingTarget FreelancerProfile entity,
                                 FreelancerProfileUpdateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateClientProfile(@MappingTarget ClientProfile entity,
                             ClientProfileUpdateRequest request);
}
