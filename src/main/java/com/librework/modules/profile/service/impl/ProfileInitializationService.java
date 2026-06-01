package com.librework.modules.profile.service.impl;


import com.librework.common.enums.ProfileType;
import com.librework.modules.profile.entity.ClientProfile;
import com.librework.modules.profile.entity.FreelancerProfile;
import com.librework.modules.profile.repository.ClientProfileRepository;
import com.librework.modules.profile.repository.FreelancerProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileInitializationService {
    private final FreelancerProfileRepository freelancerProfileRepository;
    private final ClientProfileRepository clientProfileRepository;

    public void initializeForNewUser(UUID userId, ProfileType accountType) {
        if (freelancerProfileRepository.findByUserId(userId).isEmpty()) {
            FreelancerProfile freelancerProfile = FreelancerProfile.builder()
                    .userId(userId)
                    .title("New Freelancer")
                    .build();
            freelancerProfileRepository.save(freelancerProfile);
        }

        if (accountType == ProfileType.CLIENT
                && clientProfileRepository.findByUserId(userId).isEmpty()) {
            ClientProfile clientProfile = ClientProfile.builder()
                    .userId(userId)
                    .build();
            clientProfileRepository.save(clientProfile);
        }
    }
}
