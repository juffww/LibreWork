package com.librework.modules.identity.infrastructure.mapper;

import com.librework.modules.identity.domain.entity.User;
import com.librework.modules.identity.infrastructure.entity.UserJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {
    User toDomain (UserJpaEntity entity);

    UserJpaEntity toEntity (User domain);
}
