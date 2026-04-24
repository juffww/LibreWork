package com.librework.modules.profile.application.dto.response;

import com.librework.modules.profile.domain.entity.Availability;
import com.librework.modules.profile.domain.entity.ExperienceLevel;
import lombok.*;

import java.math.BigDecimal;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class FreelancerProfileResponse {
    private String title;
    private BigDecimal hourlyRate;
    private ExperienceLevel experienceLevel;
    private Availability availability;
    private BigDecimal totalEarned;
    private String overview;
    private Map<String, String> socialLinks;
}
