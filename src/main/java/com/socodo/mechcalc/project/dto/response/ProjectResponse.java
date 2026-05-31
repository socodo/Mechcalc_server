package com.socodo.mechcalc.project.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

import com.socodo.mechcalc.project.entity.Project;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectResponse {
    UUID id;
    String name;
    String description;
    Project.ProjectStatus status;
    Instant createdAt;
    Instant updatedAt;
}
