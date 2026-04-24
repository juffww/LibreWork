package com.librework.modules.profile.application.service.impl;

import com.librework.common.enums.ProfileType;
import com.librework.common.exception.AppException;
import com.librework.common.exception.ErrorCode;
import com.librework.common.port.CurrentUserPort;
import com.librework.modules.profile.application.dto.request.ClientProfileCreationRequest;
import com.librework.modules.profile.application.dto.response.ClientProfileResponse;
import com.librework.modules.profile.application.mapper.ClientProfileMapper;
import com.librework.modules.profile.application.port.in.ClientProfileUseCase;
import com.librework.modules.profile.domain.entity.ClientProfile;
import com.librework.modules.profile.domain.repository.ClientProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientProfileServiceImpl implements ClientProfileUseCase {

    private final ClientProfileRepository clientProfileRepository;
    private final ClientProfileMapper clientProfileMapper;
    private final CurrentUserPort currentUserPort;  // thống nhất dùng port, bỏ SecurityUtils

    @Override
    @Transactional
    public ClientProfileResponse createClientProfile(ClientProfileCreationRequest request) {
        UUID userId = currentUserPort.getCurrentUserId();
        if (clientProfileRepository.existsByUserIdAndCompanyName(userId, request.getCompanyName())) {
            throw new AppException(ErrorCode.COMPANY_ALREADY_EXISTS);
        }
        ClientProfile cProfile = ClientProfile.builder()
                .companyName(request.getCompanyName())
                .userId(userId)
                .build();
        ClientProfile savedCProfile = clientProfileRepository.save(cProfile);
        return clientProfileMapper.toClientProfileResponse(savedCProfile);
    }
}