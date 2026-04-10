package com.damilola.box_service.DTOs;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ItemRequest {

    @Pattern(
            regexp = "^[a-zA-Z0-9_-]+$",
            message = "Name must contain only letters, numbers, hyphens (-), or underscores (_). Example: 'Box_123' or 'Item-1'"
    )
    private String name;

    @NotNull(message = "Weight is required. Example: 2.5")
    @Positive(message = "Weight must be greater than 0. Example: 1.5")
    private Double weight;

    @Pattern(
            regexp = "^[A-Z0-9_]+$",
            message = "Code must contain only uppercase letters, numbers, and underscores. Example: 'ITEM_001'"
    )
    private String code;
}
