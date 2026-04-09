package com.damilola.box_service.Repository;


import com.damilola.box_service.Entity.Box;
import com.damilola.box_service.enums.BoxState;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoxRepository extends JpaRepository<Box, Long> {

    // Find by txref
    Optional<Box> findByTxref(String txref);

    // Get all boxes available for loading
    List<Box> findByStateAndBatteryCapacityGreaterThanEqual(
            BoxState state, double batteryCapacity
    );

    // Optional: find boxes by state
    List<Box> findByState(BoxState state);

    boolean existsByTxref(@NotBlank @Size(max = 20) String txref);
}
