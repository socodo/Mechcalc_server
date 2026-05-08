package com.socodo.mechcalc.project.dto.request;

import lombok.Data;
import java.time.Instant;
import java.util.UUID;

@Data
public class ProjectSyncRequest {
    private UUID id;
    private String name;
    private String description;
    private String status;
    private String syncStatus;
    private Instant deletedAt;
    private Instant createdAt;
    private Instant updatedAt;
}