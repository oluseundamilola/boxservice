package com.damilola.box_service.controllers;


import com.damilola.box_service.DTOs.*;
import com.damilola.box_service.services.BoxService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.util.List;

@RestController
@RequestMapping("/api/boxes")
@RequiredArgsConstructor
@Slf4j
public class BoxController {

    private final BoxService boxService;

    @PostMapping("/create")
    public DefaultResponse<BoxResponse> createBox(
            @Valid @RequestBody CreateBoxRequest request
    ) {
        log.info("[+]Inside create box controller");
        return boxService.createBox(request);
    }

    @PostMapping("/{txRef}/load")
    public DefaultResponse<LoadBoxResponseDTO>  loadBox(
            @PathVariable String txRef,
            @Valid @RequestBody LoadBoxRequestDTO request
    ) {
        log.info("[+]Inside loadBox controller");
        return boxService.loadBox(txRef, request);
    }

    @GetMapping("/{txRef}/items")
    public DefaultResponse<List<ItemResponse>> getLoadedItems(@PathVariable String txRef) {

        log.info("[+]Inside getLoadedItems controller");
        List<ItemResponse> items = boxService.getLoadedItems(txRef);

        DefaultResponse<List<ItemResponse>> response = new DefaultResponse<>();
        response.setStatus("00");
        response.setMessage("Loaded items fetched successfully");
        response.setData(items);

        return response;
    }

    @GetMapping("/available")
    public DefaultResponse<PaginatedResponse<BoxResponseDTO>> getAvailableBoxes(
            @PageableDefault(size = 10) Pageable pageable
    ) {

        log.info("[+]Inside getAvailableBoxes controller");

        PaginatedResponse<BoxResponseDTO> page = boxService.getAvailableBoxes(pageable);

        DefaultResponse<PaginatedResponse<BoxResponseDTO>> response = new DefaultResponse<>();
        response.setStatus("00");
        response.setMessage("Available boxes fetched successfully");
        response.setData(page);

        return response;
    }

    @GetMapping("/{txRef}/battery")
    public DefaultResponse<Double> getBatteryLevel(@PathVariable String txRef) {

        log.info("[+]Inside getBatteryLevel controller");
        Double battery = boxService.getBatteryLevel(txRef);

        DefaultResponse<Double> response = new DefaultResponse<>();
        response.setStatus("00");
        response.setMessage("Battery level fetched successfully");
        response.setData(battery);

        return response;
    }
}
