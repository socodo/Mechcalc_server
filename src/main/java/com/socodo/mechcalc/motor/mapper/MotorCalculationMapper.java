package com.socodo.mechcalc.motor.mapper;

import com.socodo.mechcalc.motor.dto.request.MotorCalculationRequest;
import com.socodo.mechcalc.motor.dto.response.MotorCalculationResponse;
import com.socodo.mechcalc.motor.entity.MotorCalculation;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface MotorCalculationMapper {
    
    MotorCalculationResponse toResponse(MotorCalculation entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "selectedMotor", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(@MappingTarget MotorCalculation entity, MotorCalculationRequest request);
}
