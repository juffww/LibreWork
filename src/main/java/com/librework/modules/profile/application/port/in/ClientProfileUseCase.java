package com.librework.modules.profile.application.port.in;

import com.librework.modules.profile.application.dto.request.ClientProfileCreationRequest;
import com.librework.modules.profile.application.dto.response.ClientProfileResponse;

// application/port/in/ClientProfileUseCase.java
public interface ClientProfileUseCase {
    ClientProfileResponse createClientProfile(ClientProfileCreationRequest request);
}
