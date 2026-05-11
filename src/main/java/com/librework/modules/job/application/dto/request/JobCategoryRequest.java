package com.librework.modules.job.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.UUID;

@Getter
public class JobCategoryRequest {
    private UUID parentId;

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    @Size(max = 100)
    private String slug;

    private String iconUrl;
    private String description;
    private int sortOrder;
}