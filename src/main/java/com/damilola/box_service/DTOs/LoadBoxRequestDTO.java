package com.damilola.box_service.DTOs;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class LoadBoxRequestDTO {

    @NotEmpty
    private List<ItemRequest> items;
}
