package com.librework.modules.identity.domain.entity;

import com.librework.common.enums.ProfileType;
import com.librework.common.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private UUID activeClientProfileId;

    private LocalDateTime updatedAt;

    public void updateLanguageAndCountry(Language language, String country) {
        if (language != null) this.language = language;
        if (country != null) this.country = country;
        this.updatedAt = LocalDateTime.now();
    }

    public void switchProfile(ProfileType type, UUID clientProfileId) {
        if (type == ProfileType.CLIENT && clientProfileId == null) {
            throw new IllegalArgumentException("Khởi tạo Client profile cần có Profile ID hợp lệ");
        }
        this.activeProfileType = type;
        if (type == ProfileType.CLIENT) {
            this.activeClientProfileId = clientProfileId;
        } else {
            this.activeClientProfileId = null;
        }
        this.updatedAt = LocalDateTime.now();
    }

    public ProfileType getActiveProfileType() {
        return activeProfileType != null ? activeProfileType : ProfileType.FREELANCER;
    }
}