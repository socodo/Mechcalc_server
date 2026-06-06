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
    public ApiResponse<Void> sync(@PathVariable UUID projectId, @RequestBody MotorCalculationRequest request) {
        request.setProjectId(projectId);
        calculationService.syncCalculation(request);
        return ApiResponse.success("Lưu kết quả tính chọn động cơ thành công");
    }

    @PutMapping
    public ApiResponse<Void> update(@PathVariable UUID projectId, @RequestBody MotorCalculationRequest request) {
        request.setProjectId(projectId);
        calculationService.syncCalculation(request);
        return ApiResponse.success("Cập nhật kết quả tính chọn động cơ thành công");
    }

    @GetMapping
    public ApiResponse<MotorCalculationResponse> get(@PathVariable UUID projectId) {
        return ApiResponse.success("Lấy kết quả tính chọn động cơ thành công", calculationService.getByProject(projectId));
    }

    @DeleteMapping
    public ApiResponse<Void> delete(@PathVariable UUID projectId) {
        calculationService.deleteByProject(projectId);
        return ApiResponse.success("Xóa kết quả tính chọn động cơ thành công");
    }
}
