package com.damilola.box_service.services;


import com.damilola.box_service.DTOs.*;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface BoxService {

    DefaultResponse<BoxResponse> createBox(CreateBoxRequest request);

    DefaultResponse<LoadBoxResponseDTO> loadBox(String txRef, LoadBoxRequestDTO request);

    List<ItemResponse> getLoadedItems(String txRef);

    PaginatedResponse<BoxResponseDTO> getAvailableBoxes(Pageable pageable);

    Double getBatteryLevel(String txRef);
}

