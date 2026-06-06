package com.socodo.mechcalc.gear.service;

import com.socodo.mechcalc.gear.dto.response.GearMaterialCatalogResponse;
import com.socodo.mechcalc.gear.mapper.GearMaterialCatalogMapper;
import com.socodo.mechcalc.gear.repository.GearMaterialCatalogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GearMaterialCatalogService {

    private final GearMaterialCatalogRepository materialRepository;
    private final GearMaterialCatalogMapper materialMapper;

    @Transactional(readOnly = true)
    public List<GearMaterialCatalogResponse> getAll() {
        return materialRepository.findAll()
                .stream()
                .map(materialMapper::toResponse)
                .toList();
    }
}
