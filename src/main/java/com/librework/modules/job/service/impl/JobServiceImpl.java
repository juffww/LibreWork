package com.librework.modules.job.service.impl;

import com.librework.common.enums.*;
import com.librework.common.response.PageResponse;
import com.librework.modules.job.service.JobService;



import com.librework.exception.AppException;
import com.librework.exception.ErrorCode;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final JobMapper mapper;

    public JobDetailResponse create(JobCreationRequest request) {
        UUID currentUserId = currentUserService.getCurrentUserId();
        requireActiveProfile(ProfileType.CLIENT);
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
                request.getBudgetType(),
                request.getBudgetFixed(),
                request.getBudgetMin(),
                request.getBudgetMax(),
                request.getCurrency(),
                request.getDuration(),
                request.getExperienceLevel(),
                request.getVisibility(),
                request.isUrgent(),
                request.getFreelancersNeeded(),
                request.getSubcategoryId()
        );

        skillRequests.forEach(s -> job.addSkill(s.getSkillId(), s.isRequired()));

        Job saved = jobRepository.save(job);
        syncSkills(job);
        return toDetailResponse(job);
    }

    public JobDetailResponse getById(UUID id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found: " + id));
        job.replaceSkills(jobSkillRepository.findByJobId(id));
        return toDetailResponse(job);
    }

    public List<JobSummaryResponse> getOpenJobs() {
        return toSummaryResponses(jobRepository.findByStatus(JobStatus.OPEN));
    }

    public List<JobSummaryResponse> getMyJobs() {
        UUID currentUserId = currentUserService.getCurrentUserId();
        requireActiveProfile(ProfileType.CLIENT);
        UUID clientId = profileLookupService.getClientProfileIdByUserId(currentUserId);
        return toSummaryResponses(jobRepository.findByClientId(clientId));
    }

    public void close(UUID id) {
        UUID currentUserId = currentUserService.getCurrentUserId();
        requireActiveProfile(ProfileType.CLIENT);
        UUID clientId = profileLookupService.getClientProfileIdByUserId(currentUserId);

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found: " + id));

        if (!job.isOwner(clientId)) {
            throw new RuntimeException("You do not have permission to close this job");
        }

        job.close();
        jobRepository.save(job);
    }

    @Override
    public PageResponse<JobSummaryResponse> searchOpenJobs(String keyword, UUID categoryId, ExperienceLevel experienceLevel, BudgetType budgetType, JobDuration duration, int page, int size) {
        int safePage = Math.max(page, 0);
        int safeSize = size <= 0 ? 10 : Math.min(size, 50);
        String keywordPattern = keyword == null || keyword.isBlank()
                ? ""
                : "%" + keyword.trim().toLowerCase() + "%";

        Pageable pageable = PageRequest.of(safePage, safeSize, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Job> jobs = jobRepository.searchOpenJobs(
                keywordPattern,
                categoryId,
                experienceLevel,
                budgetType,
                duration,
                pageable
        );

        return new PageResponse<>(
                toSummaryResponses(jobs.getContent()),
                jobs.getNumber(),
                jobs.getSize(),
                jobs.getTotalElements(),
                jobs.getTotalPages(),
                jobs.isLast()
        );
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

    private List<JobSummaryResponse> toSummaryResponses(List<Job> jobs) {
        if (jobs.isEmpty()) {
            return List.of();
        }

        List<UUID> jobIds = jobs.stream()
                .map(Job::getId)
                .toList();

        List<JobSkill> jobSkills = jobSkillRepository.findByJobIdIn(jobIds);
        List<UUID> skillIds = jobSkills.stream()
                .map(JobSkill::getSkillId)
                .distinct()
                .toList();

        Map<UUID, SkillInfo> skillMap = skillIds.isEmpty()
                ? Map.of()
                : skillLookupService.findAllByIds(skillIds).stream()
                .collect(Collectors.toMap(SkillInfo::id, s -> s));

        Map<UUID, List<JobSkillResponse>> skillsByJobId = jobSkills.stream()
                .collect(Collectors.groupingBy(
                        JobSkill::getJobId,
                        Collectors.mapping(s -> JobSkillResponse.builder()
                                .skillId(s.getSkillId())
                                .skillName(skillMap.containsKey(s.getSkillId())
                                        ? skillMap.get(s.getSkillId()).name() : null)
                                .skillSlug(skillMap.containsKey(s.getSkillId())
                                        ? skillMap.get(s.getSkillId()).slug() : null)
                                .required(s.isRequired())
                                .build(), Collectors.toList())
                ));

        return jobs.stream()
                .map(job -> mapper.toSummaryResponse(
                        job,
                        skillsByJobId.getOrDefault(job.getId(), List.of())
                ))
                .toList();
    }

    private void requireActiveProfile(ProfileType expectedProfile) {
        if (currentUserService.getCurrentActiveProfileType() != expectedProfile) {
            throw new AppException(ErrorCode.FORBIDDEN);
        }
    }
}
