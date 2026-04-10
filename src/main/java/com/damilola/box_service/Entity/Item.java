package com.damilola.box_service.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString(exclude = "box")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^[a-zA-Z0-9_-]+$")
    private String name;

    @Positive
    private double weight;

    @Pattern(regexp = "^[A-Z0-9_]+$")
    @Column(unique = true)
    private String code;

    // Relationship: Many Items → One Box
    @ManyToOne
    @JoinColumn(name = "box_id")
    private Box box;
}
