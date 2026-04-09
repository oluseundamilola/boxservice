package com.damilola.box_service.services.impl;

import com.damilola.box_service.DTOs.*;
import com.damilola.box_service.Entity.Box;
import com.damilola.box_service.Entity.Item;
import com.damilola.box_service.Repository.BoxRepository;
import com.damilola.box_service.Repository.ItemRepository;
import com.damilola.box_service.enums.BoxState;
import com.damilola.box_service.services.BoxService;
import com.damilola.box_service.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoxServiceImpl implements BoxService {

    private final BoxRepository boxRepository;
    private final ItemRepository itemRepository;

    @Override
    public DefaultResponse<BoxResponse> createBox(CreateBoxRequest request) {

        // Check if txref already exists
        if (boxRepository.existsByTxref(request.getTxref())) {
            return ResponseUtil.failure("Box with this txref already exists");
        }

        // Create new Box
        Box box = new Box();
        box.setTxref(request.getTxref());
        box.setWeightLimit(request.getWeightLimit());
        box.setBatteryCapacity(request.getBatteryCapacity());

        // Default state
        box.setState(BoxState.IDLE);

        // Save
        Box savedBox = boxRepository.save(box);

        // Map to response
        BoxResponse response = new BoxResponse();
        response.setTxref(savedBox.getTxref());
        response.setWeightLimit(savedBox.getWeightLimit());
        response.setBatteryCapacity(savedBox.getBatteryCapacity());
        response.setState(savedBox.getState());
        response.setItems(null); // no items yet

        return ResponseUtil.success("Box created successfully", response);
    }


    @Override
    public DefaultResponse<LoadBoxResponseDTO> loadBox(String txRef, LoadBoxRequestDTO request) {

        // 1. Find box
        Box box = boxRepository.findByTxref(txRef)
                .orElseThrow(() -> new RuntimeException("Box not found"));

        // 2. Check battery
        if (box.getBatteryCapacity() < 25) {
            throw new RuntimeException("Battery too low to load box");
        }

        // 3. Convert DTOs to entities
        List<Item> items = request.getItems().stream().map(dto -> {
            Item item = new Item();
            item.setName(dto.getName());
            item.setWeight(dto.getWeight());
            item.setCode(dto.getCode());
            item.setBox(box);
            return item;
        }).toList();

        // 4. Calculate total weight
        double newItemsWeight = items.stream()
                .mapToDouble(Item::getWeight)
                .sum();

        double existingWeight = box.getItems() == null ? 0 :
                box.getItems().stream().mapToDouble(Item::getWeight).sum();

        double totalWeight = existingWeight + newItemsWeight;

        // 5. Validate weight limit
        if (totalWeight > 500) {
            throw new RuntimeException("Box weight limit exceeded (500g max)");
        }

        // 6. Set state
        box.setState(BoxState.LOADING);

        // 7. Save items
        itemRepository.saveAll(items);

        // 8. Attach items to box
        if (box.getItems() != null) {
            box.getItems().addAll(items);
        } else {
            box.setItems(items);
        }

        // 9. Save box
        boxRepository.save(box);

        // 10. Return response
        DefaultResponse response = new DefaultResponse<>();
        response.setStatus("00");
        response.setMessage("Success");
        return response;
    }

    @Override
    public List<ItemResponse> getLoadedItems(String txRef) {

        Box box = boxRepository.findByTxref(txRef)
                .orElseThrow(() -> new RuntimeException("Box not found"));

        if (box.getItems() == null || box.getItems().isEmpty()) {
            return List.of();
        }

        return box.getItems().stream()
                .map(item -> new ItemResponse(
                        item.getName(),
                        item.getWeight(),
                        item.getCode()
                ))
                .toList();
    }

    @Override
    public List<BoxResponseDTO> getAvailableBoxes() {

        List<Box> boxes = boxRepository.findAll();

        List<BoxResponseDTO> result = new ArrayList<>();

        for (Box box : boxes) {

            if (box.getState() != BoxState.IDLE) continue;
            if (box.getBatteryCapacity() < 25) continue;

            double currentWeight = (box.getItems() == null) ? 0 :
                    box.getItems().stream()
                            .mapToDouble(Item::getWeight)
                            .sum();

            if (currentWeight >= 500) continue;

            BoxResponseDTO dto = new BoxResponseDTO();
            dto.setTxRef(box.getTxref());
            dto.setBatteryCapacity(box.getBatteryCapacity());
            dto.setCurrentWeight(currentWeight);
            dto.setState(box.getState().name());

            result.add(dto);
        }

        return result;
    }

    @Override
    public Double getBatteryLevel(String txRef) {

        Box box = boxRepository.findByTxref(txRef)
                .orElseThrow(() -> new RuntimeException("Box not found"));

        return box.getBatteryCapacity();
    }
}
