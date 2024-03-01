package com.swiggy.catalog.model;

import com.swiggy.catalog.utils.Location;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Embedded
    private Location location;

    public Restaurant(String name, Location location) {
        this.name = name;
        this.location = location;
    }
}