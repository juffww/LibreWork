package com.librework.modules.profiles.dto.response;

import com.librework.modules.profiles.entity.FreelancerProfile;
import lombok.*;

import java.math.BigDecimal;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class FreelancerReponse {
    private String title;
    private BigDecimal hourlyRate;
    private FreelancerProfile.ExperienceLevel experienceLevel;
    private FreelancerProfile.Availability availability;
    private BigDecimal totalEarned;
    private String overview;
    private Map<String, String> socialLinks;
}
