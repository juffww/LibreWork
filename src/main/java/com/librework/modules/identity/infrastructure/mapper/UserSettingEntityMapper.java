package com.librework.modules.identity.infrastructure.mapper;

import com.librework.modules.identity.domain.entity.UserSetting;
import com.librework.modules.identity.infrastructure.entity.UserSettingJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserSettingEntityMapper {
    UserSetting toDomain (UserSettingJpaEntity entity);

    UserSettingJpaEntity toEntity (UserSetting domain);
}
