package com.damilola.box_service.DTOs;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateBoxRequest {

    @NotBlank(message = "Transaction reference (txref) must not be empty")
    @Size(max = 20, message = "Transaction reference (txref) must not exceed 20 characters")
    private String txref;

    @DecimalMax(value = "500", message = "Weight limit must not be greater than 500")
    private double weightLimit;

    private double batteryCapacity;
}
