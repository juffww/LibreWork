package com.librework.modules.profile.application.mapper;
import com.librework.modules.profile.application.dto.request.FreelancerProfileUpdateRequest;
import com.librework.modules.profile.application.dto.response.FreelancerProfileResponse;
import com.librework.modules.profile.domain.entity.FreelancerProfile;
import org.mapstruct.*;
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FreelancerProfileMapper {
    FreelancerProfileResponse toFreelancerProfileResponse(FreelancerProfile profile);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFreelancerProfile(@MappingTarget FreelancerProfile entity, FreelancerProfileUpdateRequest request);
}
