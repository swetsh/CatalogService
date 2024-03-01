package com.swiggy.catalog.dto;

import com.swiggy.catalog.utils.Location;

public record RestaurantRequest(String name, Location location) {
}
