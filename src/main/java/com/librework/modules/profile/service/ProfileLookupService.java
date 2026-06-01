package com.librework.modules.profile.service;

import com.librework.modules.profile.entity.ClientProfile;
import com.librework.modules.profile.entity.FreelancerProfile;
import com.librework.modules.profile.repository.ClientProfileRepository;
import com.librework.modules.profile.repository.FreelancerProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileLookupService {
    private final ClientProfileRepository clientProfileRepository;
    private final FreelancerProfileRepository freelancerProfileRepository;

    public UUID getClientProfileIdByUserId(UUID userId) {
        return clientProfileRepository.findByUserId(userId)
                .map(ClientProfile::getId)
                .orElseThrow(() -> new RuntimeException("Client profile not found"));
    }

    public UUID getFreelancerProfileIdByUserId(UUID userId) {
        return freelancerProfileRepository.findByUserId(userId)
                .map(FreelancerProfile::getId)
                .orElseThrow(() -> new RuntimeException("Freelancer profile not found"));
    }
}
