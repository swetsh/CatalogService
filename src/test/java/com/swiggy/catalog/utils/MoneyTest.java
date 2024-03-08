package com.swiggy.catalog.utils;

import com.swiggy.catalog.exception.NegativeAmountException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    public void testMoneyCannotBeCreatedWithNegativeAmount() {
        assertThrows(NegativeAmountException.class, () -> new Money(-1));
        assertThrows(NegativeAmountException.class, () -> new Money(-0.1));
    }

    @Test
    public void testTwoMoneyWithDifferentValueShouldNotBeEqual() {
        Money money1 = new Money(12);
        Money money2 = new Money(12.1);

        assertNotEquals(money1, money2);
    }

    @Test
    public void testTwoMoneyWithSameValueShouldBeEqual() {
        Money money1 = new Money(12.1);
        Money money2 = new Money(12.1);

        assertEquals(money1, money2);
    }


    @Test
    public void testAddingMoneyWithZero() {
        Money money1 = new Money(34);
        Money money2 = new Money(0);

        Money sum = money1.add(money2);
        Money oppositeSum = money2.add(money1);

        assertEquals(money1, sum);
        assertEquals(money1, oppositeSum);
    }

    @Test
    public void testAddingMoneyWithValue() {
        Money money1 = new Money(34);
        Money money2 = new Money(12.1);

        Money sum = money1.add(money2);
        Money oppositeSum = money2.add(money1);

        Money expectedSum = new Money(46.1);

        assertEquals(expectedSum, sum);
        assertEquals(expectedSum, oppositeSum);
    }


    @Test
    public void testSubtractingWithZero() {
        Money money1 = new Money(34);
        Money money2 = new Money(0);

        Money difference = money1.subtract(money2);

        assertEquals(money1, difference);
    }

    @Test
    public void testSubtractingWithSomeValue() {
        Money money1 = new Money(34);
        Money money2 = new Money(12.1);

        Money difference = money1.subtract(money2);

        Money expected = new Money(21.9);

        assertEquals(expected, difference);
    }

}