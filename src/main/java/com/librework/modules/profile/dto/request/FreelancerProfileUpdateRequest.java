package com.librework.modules.profile.dto.request;

import com.librework.modules.profile.entity.Availability;
import com.librework.common.enums.ExperienceLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FreelancerProfileUpdateRequest {
    private String title;
    private BigDecimal hourlyRate;
    private ExperienceLevel experienceLevel;
    private Availability availability;
//    private BigDecimal totalEarned;
    private String overview;
//    private Map<String, String> socialLinks;
}
