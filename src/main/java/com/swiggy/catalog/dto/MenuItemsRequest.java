package com.swiggy.catalog.dto;

import com.swiggy.catalog.utils.Money;

public record MenuItemsRequest (String itemName, Money price){
}
