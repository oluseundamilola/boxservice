package com.damilola.box_service.DTOs;

import lombok.Data;

@Data
public class BoxResponseDTO {
    private String txRef;
    private Double batteryCapacity;
    private Double currentWeight;
    private String state;
}
