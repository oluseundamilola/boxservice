package com.damilola.box_service.DTOs;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ItemRequest {

    @Pattern(regexp = "^[a-zA-Z0-9_-]+$")
    private String name;

    @Positive
    @NotNull
    private double weight;

    @Pattern(regexp = "^[A-Z0-9_]+$")
    private String code;
}
