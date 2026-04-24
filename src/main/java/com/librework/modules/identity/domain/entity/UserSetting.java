package com.librework.modules.identity.domain.entity;

import com.librework.common.enums.ProfileType;
import com.librework.common.enums.Language;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSetting {

    private UUID userId;

    @Builder.Default
    private String timezone = "Asia/Ho_Chi_Minh";

    @Builder.Default
    private String country = "Viet Nam";

    @Builder.Default
    private Language language = Language.vi;

    @Builder.Default
    private ProfileType activeProfileType = ProfileType.FREELANCER;

    private LocalDateTime updatedAt;

    public void updateSettings(Language language, String country, String timezone) {
        if (language != null) this.language = language;
        if (country != null) this.country = country;
        if (timezone != null) this.timezone = timezone;
        this.updatedAt = LocalDateTime.now();
    }

    public void switchProfile(ProfileType type) {
        this.activeProfileType = type;
        this.updatedAt = LocalDateTime.now();
    }

    public ProfileType getActiveProfileType() {
        return activeProfileType != null ? activeProfileType : ProfileType.FREELANCER;
    }
}