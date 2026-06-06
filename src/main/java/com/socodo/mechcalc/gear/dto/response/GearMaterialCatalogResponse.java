package com.socodo.mechcalc.gear.dto.response;

import lombok.Data;

@Data
public class GearMaterialCatalogResponse {
    private Long id;
    private String catalogCode;
    private String gearDetail;
    private String material;
    private String heatTreatment;
    private Double sizeLimitMm;
    private Integer hardnessHb;
    private Double sigmaB;
    private Double sigmaCh;
    private Double contactFatigueLimitCoefficient;
    private Double contactFatigueLimitConstant;
    private Double contactSafetyFactor;
    private Double bendingFatigueLimitCoefficient;
    private Double bendingSafetyFactor;
}
