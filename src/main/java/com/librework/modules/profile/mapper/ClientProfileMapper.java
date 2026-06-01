package com.librework.modules.profile.mapper;
import com.librework.modules.profile.dto.request.ClientProfileCreationRequest;
import com.librework.modules.profile.dto.request.ClientProfileUpdateRequest;
import com.librework.modules.profile.dto.response.ClientProfileResponse;
import com.librework.modules.profile.entity.ClientProfile;
import org.mapstruct.*;
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClientProfileMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ClientProfile toClientProfile(ClientProfileCreationRequest request);

    ClientProfileResponse toClientProfileResponse(ClientProfile profile);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateClientProfile(@MappingTarget ClientProfile entity, ClientProfileUpdateRequest request);
}
