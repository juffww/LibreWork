package com.librework.modules.profile.infrastructure.mapper;

import ch.qos.logback.core.model.ComponentModel;
import com.librework.modules.profile.domain.entity.ClientProfile;
import com.librework.modules.profile.infrastructure.entity.ClientProfileJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientProfileEntityMapper {
    ClientProfile toDomain(ClientProfileJpaEntity entity);
    ClientProfileJpaEntity toEntity(ClientProfile domain);
}
