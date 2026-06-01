package com.librework.modules.profile.controller;

import com.librework.common.response.ApiResponse;
import com.librework.modules.profile.dto.request.AddFreelancerSkillRequest;
import com.librework.modules.profile.dto.response.FreelancerSkillResponse;
import com.librework.modules.profile.service.FreelancerSkillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/freelancers/{freelancerId}/skills")
@RequiredArgsConstructor
@Tag(name = "Freelancer Skills", description = "Freelancer Skills Management (add/remove skills)")
public class FreelancerSkillController {
    private final FreelancerSkillService freelancerSkillService;

    @GetMapping
    @Operation(summary = "Get skills of a freelancer by ID", description = "View existing skills of a freelancer by freelancerId")
    public ApiResponse<List<FreelancerSkillResponse>> getSkills(@PathVariable UUID freelancerId) {
        return ApiResponse.ok(freelancerSkillService.getSkills(freelancerId));
    }

    @PostMapping
    @Operation(summary = "Add skill", description = "Freelancer adds a new skill for themselves")
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<FreelancerSkillResponse> addSkill(
            @PathVariable UUID freelancerId,
            @RequestBody @Valid AddFreelancerSkillRequest request) {
        return ApiResponse.ok(freelancerSkillService.addSkill(freelancerId, request));
    }

    @DeleteMapping("/{skillId}")
    @Operation(summary = "Remove skill", description = "Freelancer removes a skill from their profile")
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<Void> removeSkill(
            @PathVariable UUID freelancerId,
            @PathVariable UUID skillId) {
        freelancerSkillService.removeSkill(freelancerId, skillId);
        return ApiResponse.ok(null);
    }
}
