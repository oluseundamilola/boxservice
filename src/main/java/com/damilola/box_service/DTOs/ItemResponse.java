package com.damilola.box_service.DTOs;


import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ItemResponse {

    private String name;
    private double weight;
    private String code;
}
