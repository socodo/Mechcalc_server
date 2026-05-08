package com.socodo.mechcalc.project.mapper;

import com.socodo.mechcalc.project.dto.request.ProjectSyncRequest;
import com.socodo.mechcalc.project.dto.response.ProjectResponse;
import com.socodo.mechcalc.project.entity.Project;
import com.socodo.mechcalc.user.entity.User;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    @Mapping(target = "user", source = "user")
    @Mapping(target = "syncStatus", constant = "SYNCED")
    @Mapping(target = "status", source = "request.status")
    @Mapping(target = "id", source = "request.id")
    @Mapping(target = "createdAt", source = "request.createdAt")
    @Mapping(target = "updatedAt", source = "request.updatedAt")
    @Mapping(target = "deletedAt", source = "request.deletedAt")
    Project toEntity(ProjectSyncRequest request, User user);

    ProjectResponse toResponse(Project project);
}