package com.socodo.mechcalc.chain.mapper;

import com.socodo.mechcalc.chain.dto.request.ChainCalculationRequest;
import com.socodo.mechcalc.chain.dto.response.ChainCalculationResponse;
import com.socodo.mechcalc.chain.entity.ChainCalculation;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface ChainCalculationMapper {

    ChainCalculationResponse toResponse(ChainCalculation entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(@MappingTarget ChainCalculation entity, ChainCalculationRequest request);
}
