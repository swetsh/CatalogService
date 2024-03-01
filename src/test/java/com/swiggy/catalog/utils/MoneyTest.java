package com.swiggy.catalog.utils;

import com.swiggy.catalog.exception.NegativeAmountException;
import org.junit.jupiter.api.Test;

import static com.swiggy.catalog.utils.Currency.GBP;
import static com.swiggy.catalog.utils.Currency.INR;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class MoneyTest {

    @Test
    public void testMoneyCannotBeCreatedWithNegativeAmount() {
        assertThrows(NegativeAmountException.class, () -> new Money(-1, INR));
        assertThrows(NegativeAmountException.class, () -> new Money(-0.1, INR));
    }

    @Test
    public void testTwoMoneyWithDifferentValueShouldNotBeEqual() {
        Money money1 = new Money(12, INR);
        Money money2 = new Money(12.1, INR);

        assertNotEquals(money1, money2);
    }

    @Test
    public void testTwoMoneyWithDifferentCurrencyShouldNotBeEqual() {
        Money money1 = new Money(12, INR);
        Money money2 = new Money(12, GBP);

        assertNotEquals(money1, money2);
    }

    @Test
    public void testTwoMoneyWithSameValueShouldBeEqual() {
        Money money1 = new Money(12.1, INR);
        Money money2 = new Money(12.1, INR);

        assertEquals(money1, money2);
    }


    @Test
    public void testAddingMoneyWithZero() {
        Money money1 = new Money(34, INR);
        Money money2 = new Money(0, INR);

        Money sum = money1.add(money2);
        Money oppositeSum = money2.add(money1);

        assertEquals(money1, sum);
        assertEquals(money1, oppositeSum);
    }

    @Test
    public void testAddingMoneyWithValue() {
        Money money1 = new Money(34, INR);
        Money money2 = new Money(12.1, INR);

        Money sum = money1.add(money2);
        Money oppositeSum = money2.add(money1);

        Money expectedSum = new Money(46.1, INR);

        assertEquals(expectedSum, sum);
        assertEquals(expectedSum, oppositeSum);
    }

    @Test
    public void testAddingMoneyWithDifferentCurrencyShouldConvertAndAddMoney() {
        Money money = spy(new Money(300.0, INR));
        Money otherMoney = spy(new Money(1.0, GBP));


        when(money.convert(GBP)).thenReturn(new Money(3.0, GBP));
        when(otherMoney.convert(INR)).thenReturn(new Money(100.0, INR));

        Money expectedMoney = new Money(400.0, INR);
        Money otherExpectedMoney = new Money(4.0, GBP);

        assertEquals(expectedMoney, money.add(otherMoney));
        assertEquals(otherExpectedMoney, otherMoney.add(money));
    }


    @Test
    public void testSubtractingWithZero() {
        Money money1 = new Money(34, INR);
        Money money2 = new Money(0, INR);

        Money difference = money1.subtract(money2);

        assertEquals(money1, difference);
    }

    @Test
    public void testSubtractingWithSomeValue() {
        Money money1 = new Money(34, INR);
        Money money2 = new Money(12.1, INR);

        Money difference = money1.subtract(money2);

        Money expected = new Money(21.9, INR);

        assertEquals(expected, difference);
    }

    @Test
    public void testSubtractingMoneyWithDifferentCurrencyShouldConvertAndAddMoney() {
        double amount = 300;
        double otherAmount = 1;
        Money money = spy(new Money(amount, INR));
        Money otherMoney = spy(new Money(otherAmount, GBP));

        when(money.convert(GBP)).thenReturn(new Money(3.0, GBP));
        when(otherMoney.convert(INR)).thenReturn(new Money(100.0, INR));


        Money expectedMoney = new Money(200, INR);

        assertEquals(expectedMoney, money.subtract(otherMoney));
    }
}