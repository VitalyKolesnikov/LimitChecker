package org.example.limitchecker.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.example.limitchecker.TestData.*;
import static org.junit.jupiter.api.Assertions.*;

class CheckedOrdersStorageTest {

    CheckedOrdersStorage checkedOrdersStorage;
    UserStorage userStorage = new UserStorage();
    public static final double DELTA = 0.0001;

    @BeforeEach
    void setUp() {
        checkedOrdersStorage = new CheckedOrdersStorage();
        checkedOrdersStorage.addOrder(ORDER1);
        checkedOrdersStorage.addOrder(ORDER2);
        checkedOrdersStorage.addOrder(ORDER3);
    }

    @Test
    void addNullOrder() {
        assertDoesNotThrow(() -> checkedOrdersStorage.addOrder(null));
    }

    @Test
    void computePositionChange() {
        assertEquals(35, checkedOrdersStorage.computePositionChange(ORDER1));
        assertEquals(-60, checkedOrdersStorage.computePositionChange(ORDER3));
    }

    @Test
    void computeMoneyPositionChange() {
        assertEquals(4870.6, checkedOrdersStorage.computeMoneyPositionChange(ORDER1), DELTA);
        assertEquals(-8353.2, checkedOrdersStorage.computeMoneyPositionChange(ORDER3), DELTA);
    }

    @Test
    void getPassedOrdersCount() {
        assertEquals(3, checkedOrdersStorage.getPassedOrdersCount());
    }

    @Test
    void getUserPassedOrdersCount() {
        assertEquals(3, checkedOrdersStorage.getUserPassedOrdersCount(userStorage.getByName("Mike")));
    }

    @Test
    void getUserMoneyPosition() {
        assertEquals(6954.4, checkedOrdersStorage.getUserMoneyPosition(userStorage.getByName("Mike")), DELTA);
    }

    @Test
    void getSymbolPosition() {
        assertEquals(50, checkedOrdersStorage.getSymbolPosition("ETSY"));
    }

    @Test
    void getSymbolPositionPerUser() {
        assertEquals(50, checkedOrdersStorage.getSymbolPositionPerUser("ETSY", userStorage.getByName("Mike")));
    }

    @Test
    void getUserOrdersPerSymbolCount() {
        assertEquals(3, checkedOrdersStorage.getUserOrdersPerSymbolCount(userStorage.getByName("Mike"), "ETSY"));
    }
}