package org.example.limitchecker.model.limit;

import org.junit.jupiter.api.Test;

import static org.example.limitchecker.TestData.*;
import static org.junit.jupiter.api.Assertions.*;

class SymbolPositionPerUserLimitTest extends SymbolPositionLimitTest {

    Limit limit1 = new SymbolPositionPerUserLimit(-30, 100, "ETSY", USER_MIKE);
    Limit limit2 = new SymbolPositionPerUserLimit(-75, 75, "HOME", USER_JOHN);

    @Test
    void check() {
        assertTrue(limit1.check(ORDER1, checkedOrdersStorage));
        checkedOrdersStorage.addOrder(ORDER1);
        assertFalse(limit1.check(ORDER2, checkedOrdersStorage));

        assertTrue(limit2.check(ORDER4, checkedOrdersStorage));
        checkedOrdersStorage.addOrder(ORDER4);
        assertFalse(limit2.check(ORDER9, checkedOrdersStorage));
    }

    @Test
    void minPositionGreaterThanMaxPosition() {
        assertThrows(IllegalArgumentException.class, () ->
                new SymbolPositionPerUserLimit(100, 50, "ETSY", USER_MIKE));
    }
}