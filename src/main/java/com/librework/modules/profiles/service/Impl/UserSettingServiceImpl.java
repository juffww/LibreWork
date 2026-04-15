package com.librework.modules.profiles.service.Impl;

import com.librework.common.security.SecurityUtils;
import com.librework.modules.profiles.dto.request.UserSettingUpdateRequest;
import com.librework.modules.profiles.entity.UserSetting;
import com.librework.modules.profiles.mapper.UserSettingMapper;
import com.librework.modules.profiles.repository.UserSettingRepository;
import com.librework.modules.profiles.service.UserSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserSettingServiceImpl implements UserSettingService {
    private final UserSettingRepository userSettingRepository;
    private final UserSettingMapper userSettingMapper;

    @Transactional
    public UserSetting updateSetting(UserSettingUpdateRequest request)
    {
        Jwt jwt = SecurityUtils.getCurrentJwt();

        UUID userId = UUID.fromString(jwt.getClaim("userId"));

        UserSetting userSetting = userSettingRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User setting not found"));

        userSettingMapper.toUpdateSetting(userSetting, request);

        return userSettingRepository.save(userSetting);
    }
}
