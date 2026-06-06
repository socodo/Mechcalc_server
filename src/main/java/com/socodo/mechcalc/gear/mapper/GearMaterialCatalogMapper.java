package com.socodo.mechcalc.gear.mapper;

import com.socodo.mechcalc.gear.dto.response.GearMaterialCatalogResponse;
import com.socodo.mechcalc.gear.entity.GearMaterialCatalog;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface GearMaterialCatalogMapper {
    GearMaterialCatalogResponse toResponse(GearMaterialCatalog entity);
}
