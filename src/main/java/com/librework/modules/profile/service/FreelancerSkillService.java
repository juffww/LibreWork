package com.librework.modules.profile.service;

import com.librework.modules.profile.dto.request.AddFreelancerSkillRequest;
import com.librework.modules.profile.dto.response.FreelancerSkillResponse;

import java.util.List;
import java.util.UUID;

public interface FreelancerSkillService {
    FreelancerSkillResponse addSkill(UUID freelancerId, AddFreelancerSkillRequest request);

    void removeSkill(UUID freelancerId, UUID skillId);

    List<FreelancerSkillResponse> getSkills(UUID freelancerId);
}
