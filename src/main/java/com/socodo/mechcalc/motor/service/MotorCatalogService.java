package com.socodo.mechcalc.motor.service;

import com.socodo.mechcalc.motor.dto.response.MotorCatalogResponse;
import com.socodo.mechcalc.motor.mapper.MotorCatalogMapper;
import com.socodo.mechcalc.motor.repository.MotorCatalogRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MotorCatalogService {

    MotorCatalogRepository motorCatalogRepository;
    MotorCatalogMapper motorCatalogMapper;

    public List<MotorCatalogResponse> getAllCatalogs() {
        var catalogs = motorCatalogRepository.findAll();
        return motorCatalogMapper.toResponseList(catalogs);
    }
}