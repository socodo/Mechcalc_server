package com.socodo.mechcalc.gear.mapper;

import com.socodo.mechcalc.gear.dto.request.GearCalculationRequest;
import com.socodo.mechcalc.gear.dto.response.GearCalculationResponse;
import com.socodo.mechcalc.gear.entity.GearCalculation;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
        componentModel = "spring",
        builder = @Builder(disableBuilder = true)
)
public interface GearCalculationMapper {

    GearCalculationResponse toResponse(GearCalculation entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(@MappingTarget GearCalculation entity, GearCalculationRequest request);
}
