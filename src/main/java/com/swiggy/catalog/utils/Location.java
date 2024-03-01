package com.swiggy.catalog.utils;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Location {
    private final String geoCoordinate;


    public Location(String geoCoordinate) {
        this.geoCoordinate = geoCoordinate;
    }
}
