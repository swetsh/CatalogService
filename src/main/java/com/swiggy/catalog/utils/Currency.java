package com.swiggy.catalog.utils;

import lombok.Getter;

@Getter
public enum Currency {
    INR(80.0),
    USD(1.0),
    EUR(0.9),
    GBP(0.8);

    private final double exchangeRate;
    Currency(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}
