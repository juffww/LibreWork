package com.librework.modules.identity.application.mapper;

import com.librework.modules.identity.application.dto.request.UserSettingUpdateRequest;
import com.librework.modules.identity.domain.entity.UserSetting;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import com.librework.common.enums.Language;
import com.librework.modules.identity.application.dto.response.UserSettingResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserSettingMapper {
    UserSettingResponse toResponse(UserSetting setting);
}
