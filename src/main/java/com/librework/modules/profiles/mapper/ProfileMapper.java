package com.librework.modules.profiles.mapper;

import com.librework.modules.profiles.dto.request.ClientProfileCreationRequest;
import com.librework.modules.profiles.dto.response.ProfileResponse;
import com.librework.modules.profiles.entity.ClientProfile;
import com.librework.modules.profiles.entity.FreelancerProfile;
import com.librework.common.ProfileType;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProfileMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ClientProfile toClientProfile(ClientProfileCreationRequest request);

    default ProfileResponse toProfileResponse(ClientProfile clientProfile) {
        if (clientProfile == null) {
            return null;
        }
        return new ProfileResponse(ProfileType.CLIENT, clientProfile);
    }

    default ProfileResponse toProfileResponse(FreelancerProfile freelancerProfile) {
        if (freelancerProfile == null) {
            return null;
        }
        return new ProfileResponse(ProfileType.FREELANCER, freelancerProfile);
    }
}
