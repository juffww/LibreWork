package com.librework.modules.profile.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FreelancerProfile {

    private UUID id;

    private UUID userId;

    private String title;

    private BigDecimal hourlyRate;

    @Builder.Default
    private ExperienceLevel experienceLevel = ExperienceLevel.ENTRY;

    @Builder.Default
    private Availability availability = Availability.FULL_TIME;

    @Builder.Default
    private BigDecimal totalEarned = BigDecimal.ZERO;

    private String overview;

    private Map<String, String> socialLinks;

    private LocalDateTime updatedAt;

    private String avatarUrl;

    public void updateHourlyRate(BigDecimal newRate) {
        if (newRate != null && newRate.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Hourly rate cannot be negative");
        }
        this.hourlyRate = newRate;
        this.updatedAt = LocalDateTime.now();
    }

    public void addEarnings(BigDecimal amount) {
        if (amount != null && amount.compareTo(BigDecimal.ZERO) > 0) {
            this.totalEarned = this.totalEarned.add(amount);
            this.updatedAt = LocalDateTime.now();
        }
    }

    public void updateProfileInfo (String title, String overview, BigDecimal hourlyRate,
                                   ExperienceLevel experienceLevel, Availability availability)
    {
        //Check business rule
        if(hourlyRate != null && hourlyRate.compareTo(BigDecimal.ZERO) < 0)
        {
            throw new IllegalArgumentException("Hourly rate cannot be negative");
        }

        if(title != null) this.title = title;
        if(overview != null) this.overview = overview;
        this.hourlyRate = hourlyRate;

        if(experienceLevel != null) this.experienceLevel = experienceLevel;
        if(availability != null) this.availability = availability;

        this.updatedAt = LocalDateTime.now();
    }

    public void changeAvatar(String newAvatarUrl) {
        if (newAvatarUrl == null || newAvatarUrl.isBlank()) {
            throw new IllegalArgumentException("Avatar URL cannot be blank");
        }
        this.avatarUrl = newAvatarUrl;
        this.updatedAt = LocalDateTime.now();
    }
}