package com.librework.modules.profile.service.impl;

import com.librework.modules.profile.service.ClientProfileService;



import com.librework.exception.AppException;
import com.librework.exception.ErrorCode;
import com.librework.modules.identity.service.CurrentUserService;
import com.librework.modules.profile.dto.request.ClientProfileCreationRequest;
import com.librework.modules.profile.dto.response.ClientProfileResponse;
import com.librework.modules.profile.mapper.ClientProfileMapper;
import com.librework.modules.profile.entity.ClientProfile;
import com.librework.modules.profile.repository.ClientProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientProfileServiceImpl implements ClientProfileService {

    private final ClientProfileRepository clientProfileRepository;
    private final ClientProfileMapper clientProfileMapper;
    private final CurrentUserService currentUserService;  // thống nhất dùng port, bỏ SecurityUtils

    @Transactional
    public ClientProfileResponse createClientProfile(ClientProfileCreationRequest request) {
        UUID userId = currentUserService.getCurrentUserId();
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
