package com.librework.modules.identity.entity;

import com.librework.common.enums.Language;
import com.librework.common.enums.ProfileType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_settings")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSetting {

    @Id
    @Column(name = "user_id")
    private UUID userId;

    @Builder.Default
    @Column(nullable = false)
    private String timezone = "Asia/Ho_Chi_Minh";

    @Builder.Default
    @Column(nullable = false)
    private String country = "Viet Nam";

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Language language = Language.vi;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "active_profile_type", nullable = false, length = 20)
    private ProfileType activeProfileType = ProfileType.FREELANCER;

    @Builder.Default
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

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
