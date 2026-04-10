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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoxServiceImpl implements BoxService {

    private final BoxRepository boxRepository;
    private final ItemRepository itemRepository;

    @Override
    public DefaultResponse<BoxResponse> createBox(CreateBoxRequest request) {


        log.info("Checking if txref already exists");
        if (boxRepository.existsByTxref(request.getTxref())) {
            return ResponseUtil.failure("Box with this txref already exists");
        }

        log.info("Creating new box");
        Box box = new Box();
        box.setTxref(request.getTxref());
        box.setWeightLimit(request.getWeightLimit());
        box.setBatteryCapacity(request.getBatteryCapacity());

        // Default state
        box.setState(BoxState.IDLE);

        log.info("Saving box...");
        Box savedBox = boxRepository.save(box);

        // Map to response
        BoxResponse response = new BoxResponse();
        response.setTxref(savedBox.getTxref());
        response.setWeightLimit(savedBox.getWeightLimit());
        response.setBatteryCapacity(savedBox.getBatteryCapacity());
        response.setState(savedBox.getState());
        response.setItems(null);

        return ResponseUtil.success("Box created successfully", response);
    }


    @Override
    public DefaultResponse<LoadBoxResponseDTO> loadBox(String txRef, LoadBoxRequestDTO request) {

        log.info("Finding box by txRef");
        Box box = boxRepository.findByTxref(txRef)
                .orElseThrow(() -> new RuntimeException("Box not found"));

        log.info("Checking box battery...");
        if (box.getBatteryCapacity() < 25) {
            throw new RuntimeException("Battery too low to load box");
        }

        // Convert DTOs to entities
        List<Item> items = request.getItems().stream().map(dto -> {
            Item item = new Item();
            item.setName(dto.getName());
            item.setWeight(dto.getWeight());
            item.setCode(dto.getCode());
            item.setBox(box);
            return item;
        }).toList();

        log.info("Calculating total weight");
        double newItemsWeight = items.stream()
                .mapToDouble(Item::getWeight)
                .sum();

        double existingWeight = box.getItems() == null ? 0 :
                box.getItems().stream().mapToDouble(Item::getWeight).sum();

        double totalWeight = existingWeight + newItemsWeight;

        //Validate weight limit
        if (totalWeight > 500) {
            throw new RuntimeException("Box weight limit exceeded (500g max)");
        }

        // 6. Set state
        box.setState(BoxState.LOADING);

        //Save items
        itemRepository.saveAll(items);

        //Attach items to box
        if (box.getItems() != null) {
            box.getItems().addAll(items);
        } else {
            box.setItems(items);
        }

        //Save box
        boxRepository.save(box);

        //Return response
        DefaultResponse response = new DefaultResponse<>();
        response.setStatus("00");
        response.setMessage("Item loaded into box successfully");
        return response;
    }

    @Override
    public List<ItemResponse> getLoadedItems(String txRef) {

        Box box = boxRepository.findByTxref(txRef)
                .orElseThrow(() -> new RuntimeException("Box not found"));

        if (box.getItems() == null || box.getItems().isEmpty()) {
            return List.of();
        }

        List<ItemResponse> responseList = new ArrayList<>();

        for (Item item : box.getItems()) {
            ItemResponse response = new ItemResponse();
            response.setName(item.getName());
            response.setWeight(item.getWeight());
            response.setCode(item.getCode());

            responseList.add(response);
        }
        return responseList;
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
