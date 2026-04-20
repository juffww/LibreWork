package com.librework.modules.profiles.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "freelancer_profiles")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FreelancerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // Đánh dấu unique = true vì 1 User chỉ có 1 Freelancer Profile
    @Column(name = "user_id", nullable = false, unique = true)
    private UUID userId;

    @Column(name = "title", length = 200)
    private String title;

    @Column(name = "hourly_rate", precision = 10, scale = 2)
    private BigDecimal hourlyRate;

    @Enumerated(EnumType.STRING)
    @Column(name = "experience_level", nullable = false, length = 20)
    @Builder.Default
    private ExperienceLevel experienceLevel = ExperienceLevel.ENTRY;

    @Enumerated(EnumType.STRING)
    @Column(name = "availability", nullable = false, length = 20)
    @Builder.Default
    private Availability availability = Availability.FULL_TIME;

    @Column(name = "total_earned", nullable = false, precision = 14, scale = 2)
    @Builder.Default
    private BigDecimal totalEarned = BigDecimal.ZERO;

    @Column(name = "overview", columnDefinition = "TEXT")
    private String overview;

    // Xử lý cột kiểu JSONB cho PostgreSQt braL (Tuyệt chiêu của Hibernate 6)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "social_links", columnDefinition = "jsonb")
    private Map<String, String> socialLinks;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "avatar_url")
    private String avatarUrl;

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