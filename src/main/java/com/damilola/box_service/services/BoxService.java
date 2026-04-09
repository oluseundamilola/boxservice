package com.damilola.box_service.services;


import com.damilola.box_service.DTOs.*;

import java.util.List;

public interface BoxService {

    DefaultResponse<BoxResponse> createBox(CreateBoxRequest request);

    DefaultResponse<LoadBoxResponseDTO> loadBox(String txRef, LoadBoxRequestDTO request);

    List<ItemResponse> getLoadedItems(String txRef);

    List<BoxResponseDTO> getAvailableBoxes();

    Double getBatteryLevel(String txRef);
}

