package com.socodo.mechcalc.project.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.Instant;
import java.util.UUID;

@Data
public class ProjectSaveRequest {
    @NotNull(message = "Mã dự án không được để trống")
    private UUID id;

    @NotBlank(message = "Tên dự án không được để trống")
    private String name;

    private String description;
    private String status;
    private Instant deletedAt;
    private Instant createdAt;
    private Instant updatedAt;
}
