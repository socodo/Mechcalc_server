package com.socodo.mechcalc.chain.service;

import com.socodo.mechcalc.chain.dto.request.ChainCalculationRequest;
import com.socodo.mechcalc.chain.dto.response.ChainCalculationResponse;
import com.socodo.mechcalc.chain.entity.ChainCalculation;
import com.socodo.mechcalc.chain.mapper.ChainCalculationMapper;
import com.socodo.mechcalc.chain.repository.ChainCalculationRepository;
import com.socodo.mechcalc.exception.AppException;
import com.socodo.mechcalc.exception.ErrorCode;
import com.socodo.mechcalc.project.entity.Project;
import com.socodo.mechcalc.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChainCalculationService {

    private final ChainCalculationRepository calculationRepository;
    private final ProjectRepository projectRepository;
    private final ChainCalculationMapper calculationMapper;

    @Transactional
    public void syncCalculation(ChainCalculationRequest request) {
        String email = getCurrentUserEmail();
        Project project = projectRepository.findByIdAndUserEmail(request.getProjectId(), email)
                .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_FOUND));

        ChainCalculation calculation = calculationRepository.findByProjectIdAndProjectUserEmail(request.getProjectId(), email)
                .orElse(new ChainCalculation());

        if (calculation.getId() == null) {
            validateNewCalculationId(request);
            calculation.setId(request.getId() != null ? request.getId() : UUID.randomUUID());
        }

        calculationMapper.updateEntity(calculation, request);
        calculation.setProject(project);
        calculationRepository.save(calculation);
    }

    @Transactional(readOnly = true)
    public ChainCalculationResponse getByProject(UUID projectId) {
        ChainCalculation calculation = calculationRepository.findByProjectIdAndProjectUserEmail(projectId, getCurrentUserEmail())
                .orElseThrow(() -> new AppException(ErrorCode.CHAIN_CALCULATION_NOT_FOUND));
        return calculationMapper.toResponse(calculation);
    }

    private void validateNewCalculationId(ChainCalculationRequest request) {
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
