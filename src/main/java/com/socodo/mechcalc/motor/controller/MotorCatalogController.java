package com.socodo.mechcalc.motor.controller;

import com.socodo.mechcalc.common.dto.response.ApiResponse;
import com.socodo.mechcalc.motor.dto.response.MotorCatalogResponse;
import com.socodo.mechcalc.motor.service.MotorCatalogService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/motor-catalogs")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MotorCatalogController {

    MotorCatalogService motorCatalogService;

    @GetMapping
    public ApiResponse<List<MotorCatalogResponse>> getAllCatalogs() {
        List<MotorCatalogResponse> catalogs = motorCatalogService.getAllCatalogs();
        return ApiResponse.success("Lấy danh sách động cơ thành công", catalogs);
    }
}