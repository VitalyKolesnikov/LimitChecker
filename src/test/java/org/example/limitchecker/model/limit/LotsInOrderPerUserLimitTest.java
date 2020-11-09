package org.example.limitchecker.model.limit;

import org.junit.jupiter.api.Test;

import static org.example.limitchecker.TestData.*;
import static org.junit.jupiter.api.Assertions.*;

class LotsInOrderPerUserLimitTest extends LotsInOrderLimitTest {

    @Test
    void check() {
        limit = new LotsInOrderPerUserLimit(45, USER_MIKE);
        assertTrue(limit.check(ORDER1, checkedOrdersStorage));
        assertFalse(limit.check(ORDER2, checkedOrdersStorage));
    }

    @Test
    void zeroOrNegativeMaxLots() {
        assertThrows(IllegalArgumentException.class, () -> new LotsInOrderPerUserLimit(0, USER_MIKE));
        assertThrows(IllegalArgumentException.class, () -> new LotsInOrderPerUserLimit(-10, USER_MIKE));
    }

    @Test
    void userNotFound() {
        limit = new LotsInOrderPerUserLimit(45, USER_NOT_FOUND);
        assertTrue(limit.check(ORDER1, checkedOrdersStorage));
    }
}