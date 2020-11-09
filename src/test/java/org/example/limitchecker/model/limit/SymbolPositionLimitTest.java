package org.example.limitchecker.model.limit;

import org.junit.jupiter.api.Test;

import static org.example.limitchecker.TestData.*;
import static org.junit.jupiter.api.Assertions.*;

class SymbolPositionLimitTest extends AbstractLimitTest {

    Limit limit1 = new SymbolPositionLimit(-30, 100, "ETSY");
    Limit limit2 = new SymbolPositionLimit(-50, 50, "TSLA");

    @Test
    void check() {
        assertTrue(limit1.check(ORDER1, checkedOrdersStorage));
        checkedOrdersStorage.addOrder(ORDER1);
        assertFalse(limit1.check(ORDER2, checkedOrdersStorage));

        assertTrue(limit2.check(ORDER3, checkedOrdersStorage));
        checkedOrdersStorage.addOrder(ORDER3);
        assertFalse(limit2.check(ORDER6, checkedOrdersStorage));
    }

    @Test
    void minPositionGreaterThanMaxPosition() {
        assertThrows(IllegalArgumentException.class, () -> new SymbolPositionLimit(100, 50, "ETSY"));
    }
}