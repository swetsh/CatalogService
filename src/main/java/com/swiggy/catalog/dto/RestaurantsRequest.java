package com.swiggy.catalog.dto;

import com.swiggy.catalog.utils.Location;

public record RestaurantsRequest(String name, Location location) {
}
