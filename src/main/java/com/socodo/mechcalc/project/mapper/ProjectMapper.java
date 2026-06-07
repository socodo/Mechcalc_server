package com.socodo.mechcalc.project.mapper;

import com.socodo.mechcalc.project.dto.response.ProjectResponse;
import com.socodo.mechcalc.project.entity.Project;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface ProjectMapper {

    ProjectResponse toResponse(Project project);
}
