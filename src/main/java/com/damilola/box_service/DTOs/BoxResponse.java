package com.damilola.box_service.DTOs;

import com.damilola.box_service.enums.BoxState;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class BoxResponse {

    private String txref;
    private double weightLimit;
    private double batteryCapacity;
    private BoxState state;

    private List<ItemResponse> items;

}
