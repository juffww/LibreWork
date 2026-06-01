package com.librework.modules.job.service.impl;

import com.librework.modules.job.service.JobService;



import com.librework.exception.AppException;
import com.librework.exception.ErrorCode;
import com.librework.common.enums.JobStatus;
import com.librework.common.enums.ProfileType;
import com.librework.modules.identity.service.ActiveProfileService;
import com.librework.modules.profile.service.ProfileLookupService;
import com.librework.modules.identity.service.CurrentUserService;
import com.librework.modules.skill.service.SkillInfo;
import com.librework.modules.skill.service.SkillLookupService;
import com.librework.modules.job.dto.request.JobCreationRequest;
import com.librework.modules.job.dto.request.JobSkillRequest;
import com.librework.modules.job.dto.response.JobDetailResponse;
import com.librework.modules.job.dto.response.JobSkillResponse;
import com.librework.modules.job.dto.response.JobSummaryResponse;
import com.librework.modules.job.mapper.JobMapper;
import com.librework.modules.job.entity.Job;
import com.librework.modules.job.entity.JobSkill;
import com.librework.modules.job.repository.JobRepository;
import com.librework.modules.job.repository.JobSkillRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final JobSkillRepository jobSkillRepository;
    private final SkillLookupService skillLookupService;
    private final ProfileLookupService profileLookupService;
    private final CurrentUserService currentUserService;
    private final ActiveProfileService activeProfileService;
    private final JobMapper mapper;

    public JobDetailResponse create(JobCreationRequest request) {
        UUID currentUserId = currentUserService.getCurrentUserId();
        requireActiveProfile(currentUserId, ProfileType.CLIENT);
        UUID clientId = profileLookupService.getClientProfileIdByUserId(currentUserId);

        // validate skills tồn tại
        List<JobSkillRequest> skillRequests = request.getSkills() != null ? request.getSkills() : List.of();
        List<UUID> skillIds = skillRequests.stream().map(JobSkillRequest::getSkillId).toList();
        if (!skillIds.isEmpty()) {
            List<UUID> found = skillLookupService.findAllByIds(skillIds)
                    .stream().map(SkillInfo::id).toList();
            if (found.size() != skillIds.size()) {
                throw new RuntimeException("One or more skills not found");
            }
        }

        Job job = Job.create(
                clientId,
                request.getCategoryId(),
                request.getTitle(),
                request.getDescription(),
                request.getJobType(),
                request.getBudgetType(),
                request.getBudgetFixed(),
                request.getBudgetMin(),
                request.getBudgetMax(),
                request.getDuration(),
                request.getExperienceLevel(),
                request.getVisibility(),
                request.isUrgent(),
                request.getFreelancersNeeded(),
                request.getSubcategoryId()
        );

        skillRequests.forEach(s -> job.addSkill(s.getSkillId(), s.isRequired()));

        Job saved = jobRepository.save(job);
        syncSkills(saved);
        return toDetailResponse(saved);
    }

    public JobDetailResponse getById(UUID id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found: " + id));
        job.replaceSkills(jobSkillRepository.findByJobId(id));
        return toDetailResponse(job);
    }

    public List<JobSummaryResponse> getOpenJobs() {
        return jobRepository.findByStatus(JobStatus.OPEN).stream()
                .map(mapper::toSummaryResponse)
                .toList();
    }

    public List<JobSummaryResponse> getMyJobs() {
        UUID currentUserId = currentUserService.getCurrentUserId();
        requireActiveProfile(currentUserId, ProfileType.CLIENT);
        UUID clientId = profileLookupService.getClientProfileIdByUserId(currentUserId);
        return jobRepository.findByClientId(clientId).stream()
                .map(mapper::toSummaryResponse)
                .toList();
    }

    public void close(UUID id) {
        UUID currentUserId = currentUserService.getCurrentUserId();
        requireActiveProfile(currentUserId, ProfileType.CLIENT);
        UUID clientId = profileLookupService.getClientProfileIdByUserId(currentUserId);

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found: " + id));

        if (!job.isOwner(clientId)) {
            throw new RuntimeException("You do not have permission to close this job");
        }

        job.close();
        jobRepository.save(job);
    }

    // --- helper ---
    private void syncSkills(Job job) {
        jobSkillRepository.deleteByJobId(job.getId());
        List<JobSkill> skills = job.getSkills().stream()
                .map(s -> JobSkill.builder()
                        .jobId(job.getId())
                        .skillId(s.getSkillId())
                        .required(s.isRequired())
                        .build())
                .toList();
        jobSkillRepository.saveAll(skills);
    }

    private JobDetailResponse toDetailResponse(Job job) {
        // batch load skill info tránh N+1
        List<UUID> skillIds = job.getSkills().stream()
                .map(s -> s.getSkillId()).toList();

        Map<UUID, SkillInfo> skillMap = skillIds.isEmpty()
                ? Map.of()
                : skillLookupService.findAllByIds(skillIds).stream()
                  .collect(Collectors.toMap(SkillInfo::id, s -> s));

        List<JobSkillResponse> skillResponses = job.getSkills().stream()
                .map(s -> JobSkillResponse.builder()
                        .skillId(s.getSkillId())
                        .skillName(skillMap.containsKey(s.getSkillId())
                                ? skillMap.get(s.getSkillId()).name() : null)
                        .skillSlug(skillMap.containsKey(s.getSkillId())
                                ? skillMap.get(s.getSkillId()).slug() : null)
                        .required(s.isRequired())
                        .build())
                .toList();

        return mapper.toDetailResponse(job, skillResponses);
    }

    private void requireActiveProfile(UUID userId, ProfileType expectedProfile) {
        if (activeProfileService.getActiveProfileType(userId) != expectedProfile) {
            throw new AppException(ErrorCode.FORBIDDEN);
        }
    }
}
