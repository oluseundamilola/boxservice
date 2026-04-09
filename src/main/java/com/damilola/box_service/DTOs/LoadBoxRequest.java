package com.damilola.box_service.DTOs;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class LoadBoxRequest {

    @NotEmpty
    @Valid
    private List<ItemRequest> items;
}
