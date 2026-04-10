package com.damilola.box_service.Entity;

import com.damilola.box_service.enums.BoxState;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(exclude = "items")
public class Box {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 20)
    @Column(unique = true, nullable = false)
    private String txref;

    @Max(500)
    private double weightLimit;

    private double batteryCapacity;

    @Enumerated(EnumType.STRING)
    private BoxState state;

    // Relationship: One Box → Many Items
    @OneToMany(mappedBy = "box", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items = new ArrayList<>();
}
