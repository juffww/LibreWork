package com.librework.modules.profile.application.mapper;

import com.librework.common.port.SkillInfo;
import com.librework.modules.profile.application.dto.request.AddFreelancerSkillRequest;
import com.librework.modules.profile.application.dto.response.FreelancerSkillResponse;
import com.librework.modules.profile.domain.entity.FreelancerSkill;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FreelancerSkillMapper {

    default FreelancerSkillResponse toResponse(FreelancerSkill freelancerSkill, SkillInfo skill)
    {
        if(skill == null) return null;
        return new FreelancerSkillResponse
                (freelancerSkill.getSkillId(),
                        skill.name(),
                        skill.slug(),
                        freelancerSkill.getProficiencyLevel(),
                        freelancerSkill.getCreatedAt());
    }
}
