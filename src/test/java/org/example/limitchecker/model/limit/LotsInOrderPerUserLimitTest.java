package org.example.limitchecker.model.limit;

import org.junit.jupiter.api.Test;

import static org.example.limitchecker.TestData.ORDER1;
import static org.example.limitchecker.TestData.ORDER2;
import static org.junit.jupiter.api.Assertions.*;

class LotsInOrderPerUserLimitTest extends LotsInOrderLimitTest {

    Limit limit = new LotsInOrderPerUserLimit(45, userStorage.getByName("Mike"));

    @Test
    void check() {
        assertTrue(limit.check(ORDER1, checkedOrdersStorage));
        assertFalse(limit.check(ORDER2, checkedOrdersStorage));
    }

    @Test
    void zeroOrNegativeMaxLots() {
        assertThrows(IllegalArgumentException.class, () -> new LotsInOrderPerUserLimit(0, userStorage.getByName("Mike")));
        assertThrows(IllegalArgumentException.class, () -> new LotsInOrderPerUserLimit(-10, userStorage.getByName("Mike")));
    }
}