package org.example.limitchecker.model.limit;

import org.junit.jupiter.api.Test;

import static org.example.limitchecker.TestData.*;
import static org.junit.jupiter.api.Assertions.*;

class LotsInOrderPerUserLimitTest extends LotsInOrderLimitTest {

    Limit limit1 = new LotsInOrderPerUserLimit(45, USER_MIKE);
    Limit limit2 = new LotsInOrderPerUserLimit(45, USER_NOT_FOUND);

    @Test
    void check() {
        assertTrue(limit1.check(ORDER1, checkedOrdersStorage));
        assertFalse(limit1.check(ORDER2, checkedOrdersStorage));
    }

    @Test
    void zeroOrNegativeMaxLots() {
        assertThrows(IllegalArgumentException.class, () -> new LotsInOrderPerUserLimit(0, USER_MIKE));
        assertThrows(IllegalArgumentException.class, () -> new LotsInOrderPerUserLimit(-10, USER_MIKE));
    }

    @Test
    void userNotFound() {
        assertTrue(limit2.check(ORDER1, checkedOrdersStorage));
    }
}