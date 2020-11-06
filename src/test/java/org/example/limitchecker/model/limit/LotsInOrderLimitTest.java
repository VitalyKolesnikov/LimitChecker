package org.example.limitchecker.model.limit;

import org.junit.jupiter.api.Test;

import static org.example.limitchecker.TestData.ORDER1;
import static org.example.limitchecker.TestData.ORDER2;
import static org.junit.jupiter.api.Assertions.*;

class LotsInOrderLimitTest extends AbstractLimitTest {

    Limit limit = new LotsInOrderLimit(45);

    @Test
    void check() {
        assertTrue(limit.check(ORDER1, checkedOrdersStorage));
        assertFalse(limit.check(ORDER2, checkedOrdersStorage));
    }

    @Test
    void zeroOrNegativeMaxLots() {
        assertThrows(IllegalArgumentException.class, () -> new LotsInOrderLimit(0));
        assertThrows(IllegalArgumentException.class, () -> new LotsInOrderLimit(-10));
    }
}