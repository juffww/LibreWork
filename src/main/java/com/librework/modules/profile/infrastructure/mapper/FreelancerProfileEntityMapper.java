package com.librework.modules.profile.infrastructure.mapper;

import com.librework.modules.profile.domain.entity.FreelancerProfile;
import com.librework.modules.profile.infrastructure.entity.FreelancerProfileJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FreelancerProfileEntityMapper {
    FreelancerProfile toDomain(FreelancerProfileJpaEntity entity);
    FreelancerProfileJpaEntity toEntity(FreelancerProfile domain);
}