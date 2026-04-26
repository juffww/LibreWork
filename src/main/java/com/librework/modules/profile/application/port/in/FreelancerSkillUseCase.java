package com.librework.modules.profile.application.port.in;

import com.librework.modules.profile.application.dto.request.AddFreelancerSkillRequest;
import com.librework.modules.profile.application.dto.response.FreelancerSkillResponse;

import java.util.List;
import java.util.UUID;

public interface FreelancerSkillUseCase {
    FreelancerSkillResponse addSkill(UUID freelancerId, AddFreelancerSkillRequest request);
    void removeSkill(UUID freelancerId, UUID skillId);
    List<FreelancerSkillResponse> getSkills(UUID freelancerId);
}
