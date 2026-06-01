package com.librework.modules.profile.service;

import com.librework.modules.profile.dto.request.ClientProfileCreationRequest;
import com.librework.modules.profile.dto.response.ClientProfileResponse;

public interface ClientProfileService {
    ClientProfileResponse createClientProfile(ClientProfileCreationRequest request);
}
