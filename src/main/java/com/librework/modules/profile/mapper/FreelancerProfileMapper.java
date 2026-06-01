package com.librework.modules.profile.mapper;
import com.librework.modules.profile.dto.request.FreelancerProfileUpdateRequest;
import com.librework.modules.profile.dto.response.FreelancerProfileResponse;
import com.librework.modules.profile.entity.FreelancerProfile;
import org.mapstruct.*;
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FreelancerProfileMapper {
    FreelancerProfileResponse toFreelancerProfileResponse(FreelancerProfile profile);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFreelancerProfile(@MappingTarget FreelancerProfile entity, FreelancerProfileUpdateRequest request);
}
