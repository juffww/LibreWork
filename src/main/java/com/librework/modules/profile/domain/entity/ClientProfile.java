package com.librework.modules.profile.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientProfile {

    private UUID id;

    private UUID userId;

    private String companyName;

    private String logoUrl;

    private String industry;

    private String description;

    private String websiteUrl;

    @Builder.Default
    private BigDecimal totalSpent = BigDecimal.ZERO;

    @Builder.Default
    private Boolean paymentVerified = false;

    private LocalDateTime updatedAt;
}