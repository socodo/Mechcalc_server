package com.socodo.mechcalc.project.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.Instant;
import java.util.UUID;

@Data
public class ProjectSaveRequest {
    private UUID id;

    @NotBlank(message = "Tên dự án không được để trống")
    private String name;

    private String description;
    private String status;
    private Instant deletedAt;
    private Instant createdAt;
    private Instant updatedAt;
}
