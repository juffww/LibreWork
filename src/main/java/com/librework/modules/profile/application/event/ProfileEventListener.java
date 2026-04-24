package com.librework.modules.profile.application.event;

import com.librework.common.enums.ProfileType;
import com.librework.common.event.UserRegisteredEvent;
import com.librework.modules.profile.domain.entity.ClientProfile;
import com.librework.modules.profile.domain.entity.FreelancerProfile;
import com.librework.common.event.ProfileInitializedEvent;
import com.librework.modules.profile.domain.repository.ClientProfileRepository;
import com.librework.modules.profile.domain.repository.FreelancerProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProfileEventListener {

    private final FreelancerProfileRepository freelancerProfileRepository;
    private final ClientProfileRepository clientProfileRepository;
    private final ApplicationEventPublisher eventPublisher;

    @EventListener
    public void handleUserRegisteredEvent(UserRegisteredEvent event) {
        log.info("Received UserRegisteredEvent for user: {}", event.email());

        FreelancerProfile freelancerProfile = FreelancerProfile.builder()
                .userId(event.userId())
                .title("New Freelancer")
                .build();
        freelancerProfileRepository.save(freelancerProfile);

        if (event.accountType() == ProfileType.CLIENT) {
            ClientProfile clientProfile = ClientProfile.builder()
                    .userId(event.userId())
                    .build();
            ClientProfile savedClientProfile = clientProfileRepository.save(clientProfile);
        }

        eventPublisher.publishEvent(new ProfileInitializedEvent(
                event.userId(),
                event.accountType() != null ? event.accountType() : ProfileType.FREELANCER
        ));
    }
}