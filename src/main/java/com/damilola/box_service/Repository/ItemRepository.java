package com.damilola.box_service.Repository;


import com.damilola.box_service.Entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    // Find item by code
    Optional<Item> findByCode(String code);

    // Get all items in a box
    List<Item> findByBoxId(Long boxId);
}
