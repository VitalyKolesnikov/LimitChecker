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
        assertEquals(2, checkedOrdersStorage.getUserPassedOrdersCount(USER_MIKE));
        assertEquals(0, checkedOrdersStorage.getUserPassedOrdersCount(USER_SAM));
        assertEquals(0, checkedOrdersStorage.getUserPassedOrdersCount(USER_JOHN));
        checkedOrdersStorage.addOrder(ORDER3);
        checkedOrdersStorage.addOrder(ORDER4);
        checkedOrdersStorage.addOrder(ORDER5);
        assertEquals(1, checkedOrdersStorage.getUserPassedOrdersCount(USER_SAM));
        assertEquals(2, checkedOrdersStorage.getUserPassedOrdersCount(USER_JOHN));
    }

    @Test
    void getUserMoneyPosition() {
        assertNull(checkedOrdersStorage.getUserMoneyPosition(USER_MIKE));
        assertNull(checkedOrdersStorage.getUserMoneyPosition(USER_NOT_FOUND));
        checkedOrdersStorage.addOrder(ORDER1);
        checkedOrdersStorage.addOrder(ORDER2);
        assertEquals(15307.6, checkedOrdersStorage.getUserMoneyPosition(USER_MIKE), DELTA);
        checkedOrdersStorage.addOrder(ORDER6);
        assertEquals(3898.75, checkedOrdersStorage.getUserMoneyPosition(USER_MIKE), DELTA);
        checkedOrdersStorage.addOrder(ORDER3);
        checkedOrdersStorage.addOrder(ORDER4);
        checkedOrdersStorage.addOrder(ORDER5);
        assertEquals(23770.2, checkedOrdersStorage.getUserMoneyPosition(USER_JOHN), DELTA);
        assertEquals(-16056.9, checkedOrdersStorage.getUserMoneyPosition(USER_SAM), DELTA);
        checkedOrdersStorage.addOrder(ORDER7);
        assertEquals(0, checkedOrdersStorage.getUserMoneyPosition(USER_SAM), DELTA);
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
        assertNull(checkedOrdersStorage.getSymbolPositionPerUser("ETSY", USER_NOT_FOUND));
        assertNull(checkedOrdersStorage.getSymbolPositionPerUser("NNNN", USER_MIKE));
        assertNull(checkedOrdersStorage.getSymbolPositionPerUser("ETSY", USER_MIKE));
        checkedOrdersStorage.addOrder(ORDER1);
        checkedOrdersStorage.addOrder(ORDER2);
        assertEquals(110, checkedOrdersStorage.getSymbolPositionPerUser("ETSY", USER_MIKE));
        checkedOrdersStorage.addOrder(ORDER4);
        assertEquals(-60, checkedOrdersStorage.getSymbolPositionPerUser("HOME", USER_JOHN));
        checkedOrdersStorage.addOrder(ORDER8);
        assertEquals(0, checkedOrdersStorage.getSymbolPositionPerUser("HOME", USER_JOHN));
    }

    @Test
    void getUserOrdersPerSymbolCount() {
        assertEquals(0, checkedOrdersStorage.getUserOrdersPerSymbolCount(USER_MIKE, "ETSY"));
        checkedOrdersStorage.addOrder(ORDER1);
        checkedOrdersStorage.addOrder(ORDER2);
        assertEquals(2, checkedOrdersStorage.getUserOrdersPerSymbolCount(USER_MIKE, "ETSY"));
        checkedOrdersStorage.addOrder(ORDER3);
        checkedOrdersStorage.addOrder(ORDER4);
        checkedOrdersStorage.addOrder(ORDER5);
        assertEquals(1, checkedOrdersStorage.getUserOrdersPerSymbolCount(USER_SAM, "TSLA"));
        assertEquals(1, checkedOrdersStorage.getUserOrdersPerSymbolCount(USER_JOHN, "TSLA"));
        assertEquals(1, checkedOrdersStorage.getUserOrdersPerSymbolCount(USER_JOHN, "HOME"));
    }
}