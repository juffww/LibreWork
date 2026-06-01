package com.librework.modules.profile.mapper;

import com.librework.modules.skill.service.SkillInfo;
import com.librework.modules.profile.dto.request.AddFreelancerSkillRequest;
import com.librework.modules.profile.dto.response.FreelancerSkillResponse;
import com.librework.modules.profile.entity.FreelancerSkill;
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
