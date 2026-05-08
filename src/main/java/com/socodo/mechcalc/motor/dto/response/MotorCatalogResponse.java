package com.socodo.mechcalc.motor.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MotorCatalogResponse {
    Long id;
    String motorCode;
    String series;
    Double power;
    Integer speed;
    Integer syncSpeed;
    Integer poles;
    Double efficiency;
    Double cosPhi;
    Double tkTdnRatio;
    Double tmaxTdnRatio;
    Double inertia;
    Double weight;
}