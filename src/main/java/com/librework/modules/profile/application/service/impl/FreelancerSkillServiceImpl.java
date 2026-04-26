package com.librework.modules.profile.application.service.impl;

import com.librework.common.exception.AppException;
import com.librework.common.port.CurrentUserPort;
import com.librework.common.port.SkillInfo;
import com.librework.common.port.SkillQueryPort;
import com.librework.modules.profile.application.dto.request.AddFreelancerSkillRequest;
import com.librework.modules.profile.application.dto.response.FreelancerSkillResponse;
import com.librework.modules.profile.application.mapper.FreelancerSkillMapper;
import com.librework.modules.profile.application.port.in.FreelancerSkillUseCase;
import com.librework.modules.profile.domain.entity.FreelancerProfile;
import com.librework.modules.profile.domain.entity.FreelancerSkill;
import com.librework.modules.profile.domain.repository.FreelancerProfileRepository;
import com.librework.modules.profile.domain.repository.FreelancerSkillRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FreelancerSkillServiceImpl implements FreelancerSkillUseCase {
    private final FreelancerSkillRepository freelancerSkillRepository;
    private final FreelancerProfileRepository freelancerProfileRepository;
    private final SkillQueryPort skillQueryPort;
    private final CurrentUserPort currentUserPort;
    private final FreelancerSkillMapper mapper;

    @Override
    public FreelancerSkillResponse addSkill(UUID freelancerId, AddFreelancerSkillRequest request) {
        FreelancerProfile profile = freelancerProfileRepository.findById(freelancerId)
                .orElse(null);

        // only owner can add
        UUID currentUserId = currentUserPort.getCurrentUserId();

        //confirm that skill existed
        if(!skillQueryPort.existsById(request.getSkillId())){
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

        SkillInfo skill = skillQueryPort.findById(request.getSkillId())
                .orElse(null);

        return mapper.toResponse(saved, skill);
    }

    @Override
    public void removeSkill(UUID freelancerId, UUID skillId) {
        FreelancerProfile profile = freelancerProfileRepository.findById(freelancerId)
                .orElse(null);

        UUID currentUserId = currentUserPort.getCurrentUserId();
        if (!profile.getUserId().equals(currentUserId)) {
            throw new RuntimeException();
        }

        if (!freelancerSkillRepository.existsByFreelancerIdAndSkillId(freelancerId, skillId)) {
            throw new RuntimeException();
        }

        freelancerSkillRepository.deleteByFreelancerIdAndSkillId(freelancerId, skillId);
    }

    @Override
    public List<FreelancerSkillResponse> getSkills(UUID freelancerId) {
        List<FreelancerSkill> freelancerSkills = freelancerSkillRepository.findByFreelancerId(freelancerId);

        if (freelancerSkills.isEmpty()) return List.of();

        List<UUID> skillIds = freelancerSkills.stream()
                .map(FreelancerSkill::getSkillId)
                .toList();

        Map<UUID, SkillInfo> skillMap = skillQueryPort.findAllByIds(skillIds)
                .stream()
                .collect(Collectors.toMap(SkillInfo::id, s -> s));

        return freelancerSkills.stream()
                .map(fs -> mapper.toResponse(fs, skillMap.get(fs.getSkillId())))
                .toList();
    }
}
