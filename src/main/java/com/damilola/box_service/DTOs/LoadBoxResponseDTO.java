package com.damilola.box_service.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LoadBoxResponseDTO {

    private String txRef;
    private Double totalWeight;
    private List<ItemRequest> items;
}
