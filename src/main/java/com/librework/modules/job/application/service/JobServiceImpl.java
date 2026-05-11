package com.librework.modules.job.application.service;

import com.librework.common.exception.AppException;
import com.librework.common.exception.ErrorCode;
import com.librework.common.port.ClientProfileQueryPort;
import com.librework.common.port.CurrentUserPort;
import com.librework.common.port.SkillInfo;
import com.librework.common.port.SkillQueryPort;
import com.librework.modules.job.application.dto.request.JobCreationRequest;
import com.librework.modules.job.application.dto.request.JobSkillRequest;
import com.librework.modules.job.application.dto.response.JobDetailResponse;
import com.librework.modules.job.application.dto.response.JobSkillResponse;
import com.librework.modules.job.application.dto.response.JobSummaryResponse;
import com.librework.modules.job.application.mapper.JobMapper;
import com.librework.modules.job.application.port.in.JobUseCase;
import com.librework.modules.job.domain.entity.Job;
import com.librework.modules.job.domain.repository.JobRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JobServiceImpl implements JobUseCase {

    private final JobRepository jobRepository;
    private final SkillQueryPort skillQueryPort;
    private final ClientProfileQueryPort clientProfileQueryPort;
    private final CurrentUserPort currentUserPort;
    private final JobMapper mapper;

    @Override
    public JobDetailResponse create(JobCreationRequest request) {
        UUID currentUserId = currentUserPort.getCurrentUserId();
        UUID clientId = clientProfileQueryPort.findIdByUserId(currentUserId);

        // validate skills tồn tại
        List<JobSkillRequest> skillRequests = request.getSkills() != null ? request.getSkills() : List.of();
        List<UUID> skillIds = skillRequests.stream().map(JobSkillRequest::getSkillId).toList();
        if (!skillIds.isEmpty()) {
            List<UUID> found = skillQueryPort.findAllByIds(skillIds)
                    .stream().map(SkillInfo::id).toList();
            if (found.size() != skillIds.size()) {
                throw new RuntimeException();
            }
        }

        Job job = Job.builder()
                .id(UUID.randomUUID())
                .clientId(clientId)
                .categoryId(request.getCategoryId())
                .title(request.getTitle())
                .description(request.getDescription())
                .jobType(request.getJobType())
                .budgetType(request.getBudgetType())
                .budgetFixed(request.getBudgetFixed())
                .budgetMin(request.getBudgetMin())
                .budgetMax(request.getBudgetMax())
                .duration(request.getDuration())
                .experienceLevel(request.getExperienceLevel())
                .status(com.librework.common.enums.JobStatus.OPEN)
                .proposalsCount(0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        skillRequests.forEach(s -> job.addSkill(s.getSkillId(), s.isRequired()));

        Job saved = jobRepository.save(job);
        return toDetailResponse(saved);
    }

    @Override
    public JobDetailResponse getById(UUID id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
        return toDetailResponse(job);
    }

    @Override
    public List<JobSummaryResponse> getOpenJobs() {
        return jobRepository.findOpenJobs().stream()
                .map(mapper::toSummaryResponse)
                .toList();
    }

    @Override
    public List<JobSummaryResponse> getMyJobs() {
        UUID currentUserId = currentUserPort.getCurrentUserId();
        UUID clientId = clientProfileQueryPort.findIdByUserId(currentUserId);
        return jobRepository.findByClientId(clientId).stream()
                .map(mapper::toSummaryResponse)
                .toList();
    }

    @Override
    public void close(UUID id) {
        UUID currentUserId = currentUserPort.getCurrentUserId();
        UUID clientId = clientProfileQueryPort.findIdByUserId(currentUserId);

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());

        if (!job.isOwner(clientId)) {
            throw new RuntimeException();
        }

        job.close();
        jobRepository.save(job);
    }

    // --- helper ---

    private JobDetailResponse toDetailResponse(Job job) {
        // batch load skill info tránh N+1
        List<UUID> skillIds = job.getSkills().stream()
                .map(s -> s.getSkillId()).toList();

        Map<UUID, SkillInfo> skillMap = skillIds.isEmpty()
                ? Map.of()
                : skillQueryPort.findAllByIds(skillIds).stream()
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
}