package com.librework.modules.profiles.entity;

import com.librework.common.ProfileType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_settings")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSetting {

    @Id
    @Column(name = "user_id")
    private UUID userId;

    @Builder.Default
    @Column(name = "timezone", nullable = false, length = 100)
    private String timezone = "Asia/Ho_Chi_Minh";

    @Builder.Default
    @Column(name = "country", length = 100)
    private String country = "Viet Nam";

    @Builder.Default
    @Column(name = "language", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private Language language = Language.vi;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(name = "active_profile_type", nullable = false, length = 20)
    private ProfileType activeProfileType = ProfileType.FREELANCER;

    @Column(name = "active_client_profile_id")
    private UUID activeClientProfileId;  // NULL khi đang ở FREELANCER context

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}