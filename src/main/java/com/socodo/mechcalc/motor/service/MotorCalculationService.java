package com.socodo.mechcalc.motor.service;

import com.socodo.mechcalc.exception.AppException;
import com.socodo.mechcalc.exception.ErrorCode;
import com.socodo.mechcalc.motor.dto.request.MotorCalculationRequest;
import com.socodo.mechcalc.motor.dto.response.MotorCalculationResponse;
import com.socodo.mechcalc.motor.entity.MotorCalculation;
import com.socodo.mechcalc.motor.entity.MotorCatalog;
import com.socodo.mechcalc.motor.mapper.MotorCalculationMapper;
import com.socodo.mechcalc.motor.repository.MotorCalculationRepository;
import com.socodo.mechcalc.motor.repository.MotorCatalogRepository;
import com.socodo.mechcalc.project.entity.Project;
import com.socodo.mechcalc.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MotorCalculationService {

    private static final double DEFAULT_COUPLING_RATIO = 1.0;

    private final MotorCalculationRepository calculationRepository;
    private final ProjectRepository projectRepository;
    private final MotorCatalogRepository motorRepository;
    private final MotorCalculationMapper calculationMapper;

    @Transactional
    public void syncCalculation(MotorCalculationRequest request) {
        String email = getCurrentUserEmail();
        Project project = projectRepository.findByIdAndUserEmail(request.getProjectId(), email)
                .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_FOUND));

        MotorCalculation calculation = calculationRepository.findByProjectIdAndProjectUserEmail(request.getProjectId(), email)
                .orElse(new MotorCalculation());

        if (calculation.getId() == null) {
            validateNewCalculationId(request);
            calculation.setId(request.getId() != null ? request.getId() : UUID.randomUUID());
        }

        applyDefaults(request);
        calculationMapper.updateEntity(calculation, request);

        calculation.setProject(project);
        
        if (request.getSelectedMotorId() != null) {
            MotorCatalog motor = motorRepository.findById(request.getSelectedMotorId())
                    .orElseThrow(() -> new AppException(ErrorCode.INVALID_REQUEST, "Không tìm thấy động cơ đã chọn"));
            calculation.setSelectedMotor(motor);
        } else {
            calculation.setSelectedMotor(null);
        }

        calculationRepository.save(calculation);
    }

    @Transactional(readOnly = true)
    public MotorCalculationResponse getByProject(UUID projectId) {
        MotorCalculation calculation = calculationRepository.findByProjectIdAndProjectUserEmail(projectId, getCurrentUserEmail())
                .orElseThrow(() -> new AppException(ErrorCode.CALCULATION_NOT_FOUND));
        return calculationMapper.toResponse(calculation);
    }

    private void validateNewCalculationId(MotorCalculationRequest request) {
        if (request.getId() == null) {
            return;
        }

        calculationRepository.findById(request.getId())
                .filter(calculation -> !calculation.getProject().getId().equals(request.getProjectId()))
                .ifPresent(calculation -> {
                    throw new AppException(ErrorCode.INVALID_REQUEST, "Mã kết quả tính toán đã thuộc về dự án khác");
                });
    }

    private void applyDefaults(MotorCalculationRequest request) {
        if (request.getUnt() == null) {
            request.setUnt(DEFAULT_COUPLING_RATIO);
        }

        if (request.getKinematicTable() != null
                && request.getKinematicTable().getMotor() != null
                && request.getKinematicTable().getMotor().getRatio() == null) {
            request.getKinematicTable().getMotor().setRatio(request.getUnt());
        }
    }

    private String getCurrentUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
