package com.swiggy.catalog.dto;

import com.swiggy.catalog.model.Restaurant;
import com.swiggy.catalog.utils.Money;

public record MenuItemRequest(String itemName, Money price, Integer restaurantId) {
}
