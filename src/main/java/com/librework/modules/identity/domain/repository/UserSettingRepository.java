package com.librework.modules.identity.domain.repository;

import com.librework.modules.identity.domain.entity.UserSetting;
import java.util.Optional;
import java.util.UUID;

public interface UserSettingRepository {
    Optional<UserSetting> findById(UUID id);
    UserSetting save(UserSetting userSetting);
}
