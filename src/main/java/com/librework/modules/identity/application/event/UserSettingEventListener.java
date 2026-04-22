package com.librework.modules.identity.application.event;

import com.librework.common.event.ProfileInitializedEvent;
import com.librework.common.event.ActiveProfileChangedEvent;
import com.librework.modules.identity.domain.entity.UserSetting;
import com.librework.modules.identity.domain.repository.UserSettingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserSettingEventListener {

    private final UserSettingRepository userSettingRepository;

    @EventListener
    public void handleProfileInitializedEvent(ProfileInitializedEvent event) {
        log.info("Received ProfileInitializedEvent for user: {}", event.userId());

        UserSetting defaultSetting = UserSetting.builder()
                .userId(event.userId())
                .build();

        defaultSetting.switchProfile(event.accountType(), event.clientProfileId());

        userSettingRepository.save(defaultSetting);
    }

    @EventListener
    public void handleActiveProfileChangedEvent(ActiveProfileChangedEvent event) {
        log.info("Received ActiveProfileChangedEvent for user: {}", event.userId());

        UserSetting setting = userSettingRepository.findById(event.userId())
                .orElseThrow(() -> new RuntimeException("User setting not found"));

        setting.switchProfile(event.targetType(), event.targetClientProfileId());

        userSettingRepository.save(setting);
    }
}
