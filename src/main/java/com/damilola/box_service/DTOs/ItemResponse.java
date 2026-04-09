package com.damilola.box_service.DTOs;


import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ItemResponse {

    private String name;
    private double weight;
    private String code;

    public ItemResponse(@Pattern(regexp = "^[a-zA-Z0-9_-]+$") String name, @Positive double weight, @Pattern(regexp = "^[A-Z0-9_]+$") String code) {
    }
}
