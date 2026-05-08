package com.socodo.mechcalc.motor.controller;

import com.socodo.mechcalc.common.dto.response.ApiResponse;
import com.socodo.mechcalc.motor.dto.request.MotorCalculationRequest;
import com.socodo.mechcalc.motor.dto.response.MotorCalculationResponse;
import com.socodo.mechcalc.motor.service.MotorCalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/projects/{projectId}/motor-calculation")
@RequiredArgsConstructor
public class MotorCalculationController {

    private final MotorCalculationService calculationService;
    @PostMapping
    public ApiResponse<String> sync(@PathVariable UUID projectId, @RequestBody MotorCalculationRequest request) {
        request.setProjectId(projectId);
        calculationService.syncCalculation(request);
        return ApiResponse.success("Sync motor calculation successful", null);
    }

    @GetMapping
    public ApiResponse<MotorCalculationResponse> get(@PathVariable UUID projectId) {
        return ApiResponse.success("Fetch successful", calculationService.getByProject(projectId));
    }
}