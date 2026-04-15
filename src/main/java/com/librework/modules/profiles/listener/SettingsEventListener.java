package com.librework.modules.profiles.listener;

import com.librework.common.event.UserRegisteredEvent;
import com.librework.modules.profiles.entity.UserSetting;
import com.librework.modules.profiles.repository.UserSettingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class SettingsEventListener {

    private final UserSettingRepository userSettingRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleUserRegisteredEvent(UserRegisteredEvent event) {
        UserSetting defaultSetting = new UserSetting();
        defaultSetting.setUserId(event.userId());

        userSettingRepository.save(defaultSetting);
    }
}