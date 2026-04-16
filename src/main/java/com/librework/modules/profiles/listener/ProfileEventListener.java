package com.librework.modules.profiles.listener;

import com.librework.common.ProfileType;
import com.librework.common.event.UserRegisteredEvent;
import com.librework.modules.profiles.entity.ClientProfile;
import com.librework.modules.profiles.entity.FreelancerProfile;
import com.librework.modules.profiles.entity.UserSetting;
import com.librework.modules.profiles.repository.ClientProfileRepository;
import com.librework.modules.profiles.repository.FreelancerProfileRepository;
import com.librework.modules.profiles.repository.UserSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ProfileEventListener {
    // 3 Khi đăng ký xong sẽ tạo setting mặc định và tùy người dùng sẽ tạo profile freelancer hoặc client rỗng
    private final UserSettingRepository userSettingRepository;
    private final FreelancerProfileRepository freelancerProfileRepository;
    private final ClientProfileRepository clientProfileRepository;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleUserRegisteredEvent(UserRegisteredEvent event)
    {
        UserSetting defaultSetting = new UserSetting();
        defaultSetting.setUserId(event.userId());
        defaultSetting.setActiveProfileType(event.accountType());

        if(event.accountType() == ProfileType.FREELANCER)
        {
            // Khởi tạo Freelancer profile rỗng
            FreelancerProfile freelancerProfile = new FreelancerProfile();
            freelancerProfile.setUserId(event.userId());
            freelancerProfileRepository.save(freelancerProfile);

            defaultSetting.setActiveProfileType(ProfileType.FREELANCER);
        } else
        {
            // Khởi tạo client rỗng
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setUserId(event.userId());
            ClientProfile savedClientProfile = clientProfileRepository.save(clientProfile);

            defaultSetting.setActiveProfileType(ProfileType.CLIENT);
            defaultSetting.setActiveClientProfileId(savedClientProfile.getId());
        }

        userSettingRepository.save(defaultSetting);
    }
}