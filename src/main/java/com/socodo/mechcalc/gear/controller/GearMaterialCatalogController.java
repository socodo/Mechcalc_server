package com.socodo.mechcalc.gear.controller;

import com.socodo.mechcalc.common.dto.response.ApiResponse;
import com.socodo.mechcalc.gear.dto.response.GearMaterialCatalogResponse;
import com.socodo.mechcalc.gear.service.GearMaterialCatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping({"/gear-catalogs", "/gear-material-catalogs"})
@RequiredArgsConstructor
public class GearMaterialCatalogController {

    private final GearMaterialCatalogService materialService;

    @GetMapping
    public ApiResponse<List<GearMaterialCatalogResponse>> getAll() {
        return ApiResponse.success("Lấy danh sách vật liệu bánh răng thành công", materialService.getAll());
    }
}
