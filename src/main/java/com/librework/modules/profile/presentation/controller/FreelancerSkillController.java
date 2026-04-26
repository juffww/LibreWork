package com.librework.modules.profile.presentation.controller;

import com.librework.common.response.ApiResponse;
import com.librework.modules.profile.application.dto.request.AddFreelancerSkillRequest;
import com.librework.modules.profile.application.dto.response.FreelancerSkillResponse;
import com.librework.modules.profile.application.port.in.FreelancerSkillUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/freelancers/{freelancerId}/skills")
@RequiredArgsConstructor
public class FreelancerSkillController {
    private final FreelancerSkillUseCase freelancerSkillUseCase;

    @GetMapping
    public ApiResponse<List<FreelancerSkillResponse>> getSkills(@PathVariable UUID freelancerId) {
        return ApiResponse.ok(freelancerSkillUseCase.getSkills(freelancerId));
    }

    @PostMapping
    public ApiResponse<FreelancerSkillResponse> addSkill(
            @PathVariable UUID freelancerId,
            @RequestBody @Valid AddFreelancerSkillRequest request) {
        return ApiResponse.ok(freelancerSkillUseCase.addSkill(freelancerId, request));
    }

    @DeleteMapping("/{skillId}")
    public ApiResponse<Void> removeSkill(
            @PathVariable UUID freelancerId,
            @PathVariable UUID skillId) {
        freelancerSkillUseCase.removeSkill(freelancerId, skillId);
        return ApiResponse.ok(null);
    }
}
