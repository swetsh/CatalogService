package com.swiggy.catalog.utils;

import com.swiggy.catalog.exception.NegativeAmountException;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;


@Embeddable
@Getter
public class Money {
    private final double amount;

    @Enumerated(EnumType.STRING)
    private final Currency currency;

    public Money() {
        this.amount = 0;
        this.currency = Currency.INR;
    }

    public Money(double amount, Currency currency) {
        if (amount < 0)
            throw new NegativeAmountException();
        this.amount = amount;
        this.currency = currency;
    }

    public Money add(Money other) {
        if (currency != other.currency) {
            other = other.convert(this.currency);
        }

        return new Money(this.amount + other.amount, this.currency);
    }

    public Money subtract(Money other) {
        if (currency != other.currency) {
            other = other.convert(this.currency);
        }

        return new Money(this.amount - other.amount, this.currency);
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

    public Money convert(Currency toCurrency) {
        double convertedAmount = (this.amount * this.currency.getExchangeRate())/toCurrency.getExchangeRate();
        return new Money(convertedAmount, toCurrency);
    }
}
