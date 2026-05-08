package com.socodo.mechcalc.motor.mapper;

import com.socodo.mechcalc.motor.dto.request.MotorCalculationRequest;
import com.socodo.mechcalc.motor.dto.response.MotorCalculationResponse;
import com.socodo.mechcalc.motor.entity.MotorCalculation;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MotorCalculationMapper {
    
    MotorCalculationResponse toResponse(MotorCalculation entity);

    void updateEntity(@MappingTarget MotorCalculation entity, MotorCalculationRequest request);
}