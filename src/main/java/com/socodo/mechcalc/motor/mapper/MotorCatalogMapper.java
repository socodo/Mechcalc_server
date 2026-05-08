package com.socodo.mechcalc.motor.mapper;

import com.socodo.mechcalc.motor.dto.response.MotorCatalogResponse;
import com.socodo.mechcalc.motor.entity.MotorCatalog;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MotorCatalogMapper {
    MotorCatalogResponse toResponse(MotorCatalog motorCatalog);
    List<MotorCatalogResponse> toResponseList(List<MotorCatalog> motorCatalogs);
}