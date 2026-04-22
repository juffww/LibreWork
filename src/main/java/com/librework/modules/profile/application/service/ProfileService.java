package com.librework.modules.profile.application.service;

import com.librework.common.enums.ProfileType;
import com.librework.modules.profile.application.dto.request.ClientProfileCreationRequest;
import com.librework.modules.profile.application.dto.response.ProfileResponse;
import com.librework.modules.profile.domain.entity.ClientProfile;

import java.util.UUID;

public interface ProfileService {
    ProfileResponse<ClientProfile> createClientProfile(ClientProfileCreationRequest request);
    ProfileResponse<?> switchAccount(ProfileType targetType, UUID targetClientProfileId);
    ProfileResponse<?> getCurrentProfile();
}
