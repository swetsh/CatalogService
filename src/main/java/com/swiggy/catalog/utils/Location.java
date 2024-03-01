package com.swiggy.catalog.utils;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class Location {
    private String geoCoordinate;


    public Location(String geoCoordinate) {
        this.geoCoordinate = geoCoordinate;
    }
}
