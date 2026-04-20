package com.librework.modules.profiles.dto.request;

import com.librework.modules.profiles.entity.FreelancerProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FreelancerProfileUpdateRequest {
    private String title;
    private BigDecimal hourlyRate;
    private FreelancerProfile.ExperienceLevel experienceLevel;
    private FreelancerProfile.Availability availability;
    private BigDecimal totalEarned;
    private String overview;
    private Map<String, String> socialLinks;
}
