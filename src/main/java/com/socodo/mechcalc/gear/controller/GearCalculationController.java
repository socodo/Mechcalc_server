package com.socodo.mechcalc.gear.controller;

import com.socodo.mechcalc.common.dto.response.ApiResponse;
import com.socodo.mechcalc.gear.dto.request.GearCalculationRequest;
import com.socodo.mechcalc.gear.dto.response.GearCalculationResponse;
import com.socodo.mechcalc.gear.service.GearCalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/projects/{projectId}/gear-calculation")
@RequiredArgsConstructor
public class GearCalculationController {

    private final GearCalculationService calculationService;

    @PostMapping
    public ApiResponse<Void> save(@PathVariable UUID projectId, @RequestBody GearCalculationRequest request) {
        request.setProjectId(projectId);
        calculationService.saveCalculation(request);
        return ApiResponse.success("Lưu kết quả tính bộ truyền bánh răng thành công");
    }

    @GetMapping
    public ApiResponse<GearCalculationResponse> get(@PathVariable UUID projectId) {
        return ApiResponse.success("Lấy kết quả tính bộ truyền bánh răng thành công", calculationService.getByProject(projectId));
    }
}
