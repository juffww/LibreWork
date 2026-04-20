package com.librework.modules.profiles.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ClientProfileResponse {
    private String companyName;
    private String logoUrl;
    private String industry;
    private String description;
    private String websiteUrl;
    private BigDecimal totalSpent;
    private Boolean paymentVerified;
}
