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

    public enum ExperienceLevel {
        ENTRY,
        INTERMEDIATE,
        EXPERT
    }

    public enum Availability {
        FULL_TIME,
        PART_TIME,
        NOT_AVAILABLE
    }
}