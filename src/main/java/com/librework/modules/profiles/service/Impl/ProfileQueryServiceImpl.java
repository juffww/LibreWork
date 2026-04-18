package com.librework.modules.profiles.service.Impl;

import com.librework.modules.profiles.dto.response.UserProfileSummary;
import com.librework.modules.profiles.entity.FreelancerProfile;
import com.librework.modules.profiles.repository.ClientProfileRepository;
import com.librework.modules.profiles.repository.FreelancerProfileRepository;
import com.librework.modules.profiles.service.ProfileQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileQueryServiceImpl implements ProfileQueryService {

    private final FreelancerProfileRepository freelancerProfileRepository;
    private final ClientProfileRepository clientProfileRepository;

    @Override
    @Transactional(readOnly = true)
    public UserProfileSummary getUserProfiles(UUID userId) {
        UUID freelancerProfileId = freelancerProfileRepository.findByUserId(userId)
                .map(FreelancerProfile::getId)
                .orElse(null);

        var clientProfiles = clientProfileRepository.findAllByUserId(userId)
                .stream()
                .map(cp -> UserProfileSummary.ClientProfileInfo.builder()
                        .id(cp.getId())
                        .displayName(cp.getCompanyName())
                        .avatarUrl(cp.getLogoUrl())
                        .build())
                .collect(Collectors.toList());

        return UserProfileSummary.builder()
                .freelancerProfileId(freelancerProfileId)
                .clientProfiles(clientProfiles)
                .build();
    }
}
