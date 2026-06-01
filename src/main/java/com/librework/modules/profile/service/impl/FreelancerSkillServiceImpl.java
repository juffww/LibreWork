package com.librework.modules.profile.service.impl;

import com.librework.modules.profile.service.FreelancerSkillService;



import com.librework.exception.AppException;
import com.librework.exception.ErrorCode;
import com.librework.common.enums.ProfileType;
import com.librework.modules.identity.service.ActiveProfileService;
import com.librework.modules.identity.service.CurrentUserService;
import com.librework.modules.skill.service.SkillInfo;
import com.librework.modules.skill.service.SkillLookupService;
import com.librework.modules.profile.dto.request.AddFreelancerSkillRequest;
import com.librework.modules.profile.dto.response.FreelancerSkillResponse;
import com.librework.modules.profile.mapper.FreelancerSkillMapper;
import com.librework.modules.profile.entity.FreelancerProfile;
import com.librework.modules.profile.entity.FreelancerSkill;
import com.librework.modules.profile.repository.FreelancerProfileRepository;
import com.librework.modules.profile.repository.FreelancerSkillRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FreelancerSkillServiceImpl implements FreelancerSkillService {
    private final FreelancerSkillRepository freelancerSkillRepository;
    private final FreelancerProfileRepository freelancerProfileRepository;
    private final SkillLookupService skillLookupService;
    private final CurrentUserService currentUserService;
    private final ActiveProfileService activeProfileService;
    private final FreelancerSkillMapper mapper;

    public FreelancerSkillResponse addSkill(UUID freelancerId, AddFreelancerSkillRequest request) {
        FreelancerProfile profile = freelancerProfileRepository.findById(freelancerId)
                .orElseThrow(() -> new RuntimeException("Freelancer profile not found"));

        // only owner can add
        UUID currentUserId = currentUserService.getCurrentUserId();
        requireFreelancerProfile(currentUserId);
        if (!profile.getUserId().equals(currentUserId)) {
            throw new AppException(ErrorCode.FORBIDDEN);
        }

        //confirm that skill existed
        if(!skillLookupService.existsById(request.getSkillId())){
            throw new RuntimeException();
        }

        if (freelancerSkillRepository.existsByFreelancerIdAndSkillId(freelancerId, request.getSkillId())) {
            throw new RuntimeException("Skill already exists");
        }

        FreelancerSkill freelancerSkill = FreelancerSkill.builder()
                .freelancerId(freelancerId)
                .skillId(request.getSkillId())
                .proficiencyLevel(request.getProficiencyLevel())
                .createdAt(LocalDateTime.now())
                .build();

        FreelancerSkill saved = freelancerSkillRepository.save(freelancerSkill);

        SkillInfo skill = skillLookupService.findById(request.getSkillId())
                .orElse(null);

        return mapper.toResponse(saved, skill);
    }

    public void removeSkill(UUID freelancerId, UUID skillId) {
        FreelancerProfile profile = freelancerProfileRepository.findById(freelancerId)
                .orElseThrow(() -> new RuntimeException("Freelancer profile not found"));

        UUID currentUserId = currentUserService.getCurrentUserId();
        requireFreelancerProfile(currentUserId);
        if (!profile.getUserId().equals(currentUserId)) {
            throw new AppException(ErrorCode.FORBIDDEN);
        }

        if (!freelancerSkillRepository.existsByFreelancerIdAndSkillId(freelancerId, skillId)) {
            throw new RuntimeException();
        }

        freelancerSkillRepository.deleteByFreelancerIdAndSkillId(freelancerId, skillId);
    }

    public List<FreelancerSkillResponse> getSkills(UUID freelancerId) {
        List<FreelancerSkill> freelancerSkills = freelancerSkillRepository.findByFreelancerId(freelancerId);

        if (freelancerSkills.isEmpty()) return List.of();

        List<UUID> skillIds = freelancerSkills.stream()
                .map(FreelancerSkill::getSkillId)
                .toList();

        Map<UUID, SkillInfo> skillMap = skillLookupService.findAllByIds(skillIds)
                .stream()
                .collect(Collectors.toMap(SkillInfo::id, s -> s));

        return freelancerSkills.stream()
                .map(fs -> mapper.toResponse(fs, skillMap.get(fs.getSkillId())))
                .toList();
    }

    private void requireFreelancerProfile(UUID userId) {
        if (activeProfileService.getActiveProfileType(userId) != ProfileType.FREELANCER) {
            throw new AppException(ErrorCode.FORBIDDEN);
        }
    }
}
