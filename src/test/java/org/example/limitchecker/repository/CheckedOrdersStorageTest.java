package org.example.limitchecker.repository;

import org.example.limitchecker.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.example.limitchecker.TestData.*;
import static org.junit.jupiter.api.Assertions.*;

class CheckedOrdersStorageTest {

    CheckedOrdersStorage storage;
    public static final double DELTA = 0.0001;

    @BeforeEach
    void setUp() {
        storage = new CheckedOrdersStorage();
        storage.addOrder(ORDER1);
        storage.addOrder(ORDER2);
        storage.addOrder(ORDER3);
    }

    @Test
    void computePositionChange() {
        assertEquals(35, storage.computePositionChange(ORDER1));
        assertEquals(-60, storage.computePositionChange(ORDER3));
    }

    @Test
    void computeMoneyPositionChange() {
        assertEquals(4870.6, storage.computeMoneyPositionChange(ORDER1), DELTA);
        assertEquals(-8353.2, storage.computeMoneyPositionChange(ORDER3), DELTA);
    }

    @Test
    void getPassedOrdersCount() {
        assertEquals(3, storage.getPassedOrdersCount());
    }

    @Test
    void getUserPassedOrdersCount() {
        assertEquals(3, storage.getUserPassedOrdersCount(User.MIKE));
    }

    @Test
    void getUserMoneyPosition() {
        assertEquals(6954.4, storage.getUserMoneyPosition(User.MIKE), DELTA);
    }

    @Test
    void getSymbolPosition() {
        assertEquals(50, storage.getSymbolPosition("ETSY"));
    }

    @Test
    void getSymbolPositionPerUser() {
        assertEquals(50, storage.getSymbolPositionPerUser("ETSY", User.MIKE));
    }

    @Test
    void getUserOrdersPerSymbolCount() {
        assertEquals(3, storage.getUserOrdersPerSymbolCount(User.MIKE, "ETSY"));
    }
}