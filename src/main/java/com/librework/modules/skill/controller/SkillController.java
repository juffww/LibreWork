package com.librework.modules.skill.controller;

import com.librework.common.response.ApiResponse;
import com.librework.modules.skill.dto.response.SkillResponse;
import com.librework.modules.skill.service.SkillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/skills")
@Tag(name = "Skills", description = "Skill reference data management (common Skill dictionary)")
public class SkillController {
    private final SkillService skillService;

    @GetMapping("/{id}")
    @Operation(summary = "Get Skill info", description = "Get details of a skill by id")
    public ApiResponse<SkillResponse> getById(@PathVariable UUID id) {
        return ApiResponse.ok(skillService.getById(id));
    }

    @GetMapping
    @Operation(summary = "Get all or by Category", description = "Get all skills or query by skill category ID")
    public ApiResponse<List<SkillResponse>> getAll(
            @RequestParam(required = false) UUID categoryId
    ) {
        if (categoryId != null) {
            return ApiResponse.ok(skillService.getByCategoryId(categoryId));
        }
        return ApiResponse.ok(skillService.getAll());
    }

}
