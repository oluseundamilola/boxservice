package com.damilola.box_service.DTOs;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateBoxRequest {

    @NotBlank
    @Size(max = 20)
    private String txref;

    @Max(500)
    private double weightLimit;

    private double batteryCapacity;
}
