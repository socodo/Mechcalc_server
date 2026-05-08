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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MotorCalculationService {

    private final MotorCalculationRepository calculationRepository;
    private final ProjectRepository projectRepository;
    private final MotorCatalogRepository motorRepository;
    private final MotorCalculationMapper calculationMapper;

    @Transactional
    public void syncCalculation(MotorCalculationRequest request) {
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_FOUND));

        MotorCalculation calculation = calculationRepository.findByProjectId(request.getProjectId())
                .orElse(new MotorCalculation());

        calculationMapper.updateEntity(calculation, request);

        calculation.setId(request.getId()); 
        calculation.setProject(project);
        
        if (request.getSelectedMotorId() != null) {
            MotorCatalog motor = motorRepository.findById(request.getSelectedMotorId()).orElse(null);
            calculation.setSelectedMotor(motor);
        }

        calculation.setSyncStatus("SYNCED");

        calculationRepository.save(calculation);
    }

    public MotorCalculationResponse getByProject(UUID projectId) {
        MotorCalculation calculation = calculationRepository.findByProjectId(projectId)
                .orElseThrow(() -> new AppException(ErrorCode.CALCULATION_NOT_FOUND));
        return calculationMapper.toResponse(calculation);
    }
}