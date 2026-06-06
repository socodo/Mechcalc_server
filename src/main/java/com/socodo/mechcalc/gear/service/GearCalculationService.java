package com.socodo.mechcalc.gear.service;

import com.socodo.mechcalc.exception.AppException;
import com.socodo.mechcalc.exception.ErrorCode;
import com.socodo.mechcalc.gear.dto.request.GearCalculationRequest;
import com.socodo.mechcalc.gear.dto.response.GearCalculationResponse;
import com.socodo.mechcalc.gear.entity.GearCalculation;
import com.socodo.mechcalc.gear.mapper.GearCalculationMapper;
import com.socodo.mechcalc.gear.repository.GearCalculationRepository;
import com.socodo.mechcalc.project.entity.Project;
import com.socodo.mechcalc.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GearCalculationService {

    private final GearCalculationRepository calculationRepository;
    private final ProjectRepository projectRepository;
    private final GearCalculationMapper calculationMapper;

    @Transactional
    public void saveCalculation(GearCalculationRequest request) {
        String email = getCurrentUserEmail();
        Project project = projectRepository.findByIdAndUserEmail(request.getProjectId(), email)
                .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_FOUND));

        GearCalculation calculation = calculationRepository.findByProjectIdAndProjectUserEmail(request.getProjectId(), email)
                .orElse(new GearCalculation());

        if (calculation.getId() == null) {
            validateNewCalculationId(request);
            calculation.setId(request.getId() != null ? request.getId() : UUID.randomUUID());
        }

        calculationMapper.updateEntity(calculation, request);
        calculation.setProject(project);

        calculationRepository.save(calculation);
    }

    @Transactional(readOnly = true)
    public GearCalculationResponse getByProject(UUID projectId) {
        GearCalculation calculation = calculationRepository.findByProjectIdAndProjectUserEmail(projectId, getCurrentUserEmail())
                .orElseThrow(() -> new AppException(ErrorCode.GEAR_CALCULATION_NOT_FOUND));
        return calculationMapper.toResponse(calculation);
    }

    private void validateNewCalculationId(GearCalculationRequest request) {
        if (request.getId() == null) {
            return;
        }

        calculationRepository.findById(request.getId())
                .filter(calculation -> !calculation.getProject().getId().equals(request.getProjectId()))
                .ifPresent(calculation -> {
                    throw new AppException(ErrorCode.INVALID_REQUEST, "Mã kết quả tính toán đã thuộc về dự án khác");
                });
    }

    private String getCurrentUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
