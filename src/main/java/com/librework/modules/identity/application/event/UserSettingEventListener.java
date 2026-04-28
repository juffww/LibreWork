package com.librework.modules.identity.application.event;

import com.librework.modules.profile.domain.event.ProfileInitializedEvent;
import com.librework.modules.identity.application.port.in.UserSettingUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserSettingEventListener {

    private final UserSettingUseCase userSettingUseCase;

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleProfileInitializedEvent(ProfileInitializedEvent event) {
        log.info("Received ProfileInitializedEvent for user: {}", event.userId());
        userSettingUseCase.initDefaultSetting(event.userId(), event.accountType()); // gọi qua use case
    }
}