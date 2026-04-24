package com.librework.modules.identity.application.event;

import com.librework.common.event.ProfileInitializedEvent;
import com.librework.modules.identity.application.port.in.UserSettingUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserSettingEventListener {

    private final UserSettingUseCase userSettingUseCase;

    @EventListener
    public void handleProfileInitializedEvent(ProfileInitializedEvent event) {
        log.info("Received ProfileInitializedEvent for user: {}", event.userId());
        userSettingUseCase.initDefaultSetting(event.userId(), event.accountType()); // gọi qua use case
    }
}