package com.swiggy.catalog.utils;

import com.swiggy.catalog.exception.NegativeAmountException;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.Currency;


@Embeddable
@Getter
public class Money {
    private final double amount;

    private final Currency currency;

    public Money() {
        this.amount = 0;
        this.currency = Currency.getInstance("INR");
    }

    public Money(double amount) {
        if (amount < 0)
            throw new NegativeAmountException();
        this.amount = amount;
        this.currency = Currency.getInstance("INR");
    }

    public Money add(Money other) {
        return new Money(this.amount + other.amount);
    }

    public Money subtract(Money other) {
        return new Money(this.amount - other.amount);
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }

        if (!(object instanceof Money otherMoney)) {
            return false;
        }

        return Double.compare(amount, otherMoney.amount) == 0 && currency == otherMoney.currency;
    }

}
