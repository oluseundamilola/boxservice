package com.damilola.box_service.controllers;


import com.damilola.box_service.DTOs.*;
import com.damilola.box_service.services.BoxService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boxes")
@RequiredArgsConstructor
public class BoxController {

    private final BoxService boxService;

    @PostMapping
    public DefaultResponse<BoxResponse> createBox(
            @Valid @RequestBody CreateBoxRequest request
    ) {
        return boxService.createBox(request);
    }

    @PostMapping("/{txRef}/load")
    public DefaultResponse<LoadBoxResponseDTO>  loadBox(
            @PathVariable String txRef,
            @RequestBody LoadBoxRequestDTO request
    ) {
        return boxService.loadBox(txRef, request);
    }

    @GetMapping("/{txRef}/items")
    public DefaultResponse<List<ItemResponse>> getLoadedItems(@PathVariable String txRef) {

        List<ItemResponse> items = boxService.getLoadedItems(txRef);

        DefaultResponse<List<ItemResponse>> response = new DefaultResponse<>();
        response.setStatus("00");
        response.setMessage("Loaded items fetched successfully");
        response.setData(items);

        return response;
    }

    @GetMapping("/available")
    public DefaultResponse<List<BoxResponseDTO>> getAvailableBoxes() {

        List<BoxResponseDTO> boxes = boxService.getAvailableBoxes();

        DefaultResponse<List<BoxResponseDTO>> response = new DefaultResponse<>();
        response.setStatus("00");
        response.setMessage("Available boxes fetched successfully");
        response.setData(boxes);

        return response;
    }

    @GetMapping("/{txRef}/battery")
    public DefaultResponse<Double> getBatteryLevel(@PathVariable String txRef) {

        Double battery = boxService.getBatteryLevel(txRef);

        DefaultResponse<Double> response = new DefaultResponse<>();
        response.setStatus("00");
        response.setMessage("Battery level fetched successfully");
        response.setData(battery);

        return response;
    }
}
