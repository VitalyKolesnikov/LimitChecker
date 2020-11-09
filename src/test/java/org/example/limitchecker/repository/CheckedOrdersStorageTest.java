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
    }

    @Test
    void addNullOrder() {
        assertDoesNotThrow(() -> checkedOrdersStorage.addOrder(null));
    }

    @Test
    void computePositionChange() {
        assertEquals(35, checkedOrdersStorage.computePositionChange(ORDER1));
        assertEquals(-38, checkedOrdersStorage.computePositionChange(ORDER3));
    }

    @Test
    void computeMoneyPositionChange() {
        assertEquals(4870.6, checkedOrdersStorage.computeMoneyPositionChange(ORDER1), DELTA);
        assertEquals(-16056.9, checkedOrdersStorage.computeMoneyPositionChange(ORDER3), DELTA);
    }

    @Test
    void getPassedOrdersCount() {
        assertEquals(0, checkedOrdersStorage.getPassedOrdersCount());
        checkedOrdersStorage.addOrder(ORDER1);
        checkedOrdersStorage.addOrder(ORDER2);
        checkedOrdersStorage.addOrder(null);
        assertEquals(2, checkedOrdersStorage.getPassedOrdersCount());
        checkedOrdersStorage.addOrder(ORDER3);
        checkedOrdersStorage.addOrder(ORDER4);
        assertEquals(4, checkedOrdersStorage.getPassedOrdersCount());
    }

    @Test
    void getUserPassedOrdersCount() {
        checkedOrdersStorage.addOrder(ORDER1);
        checkedOrdersStorage.addOrder(ORDER2);
        assertEquals(2, checkedOrdersStorage.getUserPassedOrdersCount(userStorage.getByName("Mike")));
        assertEquals(0, checkedOrdersStorage.getUserPassedOrdersCount(userStorage.getByName("Sam")));
        assertEquals(0, checkedOrdersStorage.getUserPassedOrdersCount(userStorage.getByName("John")));
        checkedOrdersStorage.addOrder(ORDER3);
        checkedOrdersStorage.addOrder(ORDER4);
        checkedOrdersStorage.addOrder(ORDER5);
        assertEquals(1, checkedOrdersStorage.getUserPassedOrdersCount(userStorage.getByName("Sam")));
        assertEquals(2, checkedOrdersStorage.getUserPassedOrdersCount(userStorage.getByName("John")));
    }

    @Test
    void getUserMoneyPosition() {
        assertNull(checkedOrdersStorage.getUserMoneyPosition(userStorage.getByName("Mike")));
        assertNull(checkedOrdersStorage.getUserMoneyPosition(userStorage.getByName("David")));
        checkedOrdersStorage.addOrder(ORDER1);
        checkedOrdersStorage.addOrder(ORDER2);
        assertEquals(15307.6, checkedOrdersStorage.getUserMoneyPosition(userStorage.getByName("Mike")), DELTA);
        checkedOrdersStorage.addOrder(ORDER6);
        assertEquals(3898.75, checkedOrdersStorage.getUserMoneyPosition(userStorage.getByName("Mike")), DELTA);
        checkedOrdersStorage.addOrder(ORDER3);
        checkedOrdersStorage.addOrder(ORDER4);
        checkedOrdersStorage.addOrder(ORDER5);
        assertEquals(23770.2, checkedOrdersStorage.getUserMoneyPosition(userStorage.getByName("John")), DELTA);
        assertEquals(-16056.9, checkedOrdersStorage.getUserMoneyPosition(userStorage.getByName("Sam")), DELTA);
        checkedOrdersStorage.addOrder(ORDER7);
        assertEquals(0, checkedOrdersStorage.getUserMoneyPosition(userStorage.getByName("Sam")), DELTA);
    }

    @Test
    void getSymbolPosition() {
        assertNull(checkedOrdersStorage.getSymbolPosition("HOME"));
        checkedOrdersStorage.addOrder(ORDER3);
        checkedOrdersStorage.addOrder(ORDER5);
        checkedOrdersStorage.addOrder(ORDER6);
        assertEquals(-5, checkedOrdersStorage.getSymbolPosition("TSLA"));
        checkedOrdersStorage.addOrder(ORDER1);
        checkedOrdersStorage.addOrder(ORDER2);
        assertEquals(110, checkedOrdersStorage.getSymbolPosition("ETSY"));
        checkedOrdersStorage.addOrder(ORDER4);
        assertEquals(-60, checkedOrdersStorage.getSymbolPosition("HOME"));
        checkedOrdersStorage.addOrder(ORDER8);
        assertEquals(0, checkedOrdersStorage.getSymbolPosition("HOME"));
    }

    @Test
    void getSymbolPositionPerUser() {
        assertNull(checkedOrdersStorage.getSymbolPositionPerUser("ETSY", userStorage.getByName("David")));
        assertNull(checkedOrdersStorage.getSymbolPositionPerUser("NNNN", userStorage.getByName("Mike")));
        assertNull(checkedOrdersStorage.getSymbolPositionPerUser("ETSY", userStorage.getByName("Mike")));
        checkedOrdersStorage.addOrder(ORDER1);
        checkedOrdersStorage.addOrder(ORDER2);
        assertEquals(110, checkedOrdersStorage.getSymbolPositionPerUser("ETSY", userStorage.getByName("Mike")));
        checkedOrdersStorage.addOrder(ORDER4);
        assertEquals(-60, checkedOrdersStorage.getSymbolPositionPerUser("HOME", userStorage.getByName("John")));
        checkedOrdersStorage.addOrder(ORDER8);
        assertEquals(0, checkedOrdersStorage.getSymbolPositionPerUser("HOME", userStorage.getByName("John")));
    }

    @Test
    void getUserOrdersPerSymbolCount() {
        assertEquals(0, checkedOrdersStorage.getUserOrdersPerSymbolCount(userStorage.getByName("Mike"), "ETSY"));
        checkedOrdersStorage.addOrder(ORDER1);
        checkedOrdersStorage.addOrder(ORDER2);
        assertEquals(2, checkedOrdersStorage.getUserOrdersPerSymbolCount(userStorage.getByName("Mike"), "ETSY"));
        checkedOrdersStorage.addOrder(ORDER3);
        checkedOrdersStorage.addOrder(ORDER4);
        checkedOrdersStorage.addOrder(ORDER5);
        assertEquals(1, checkedOrdersStorage.getUserOrdersPerSymbolCount(userStorage.getByName("Sam"), "TSLA"));
        assertEquals(1, checkedOrdersStorage.getUserOrdersPerSymbolCount(userStorage.getByName("John"), "TSLA"));
        assertEquals(1, checkedOrdersStorage.getUserOrdersPerSymbolCount(userStorage.getByName("John"), "HOME"));
    }
}