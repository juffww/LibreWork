package com.librework.modules.skill.controller;

import com.librework.common.response.ApiResponse;
import com.librework.modules.skill.dto.response.SkillCategoryResponse;
import com.librework.modules.skill.service.SkillCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/skill-categories")
@RequiredArgsConstructor
@Tag(name = "Skill Categories", description = "Skill Category Management")
public class SkillCategoryController {
    private final SkillCategoryService skillCategoryService;

    @GetMapping
    @Operation(summary = "Get all skill categories")
    public ApiResponse<List<SkillCategoryResponse>> getAll() {
        return ApiResponse.ok(skillCategoryService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get details of a skill category")
    public ApiResponse<SkillCategoryResponse> getById(@PathVariable UUID id)
    {
        return ApiResponse.ok(skillCategoryService.getById(id));
    }
}
