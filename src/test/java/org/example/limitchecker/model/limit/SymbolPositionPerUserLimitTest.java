package org.example.limitchecker.model.limit;

import org.junit.jupiter.api.Test;

import static org.example.limitchecker.TestData.*;
import static org.junit.jupiter.api.Assertions.*;

class SymbolPositionPerUserLimitTest extends SymbolPositionLimitTest {

    Limit limit = new SymbolPositionPerUserLimit(-50, 100, "ETSY", userStorage.getByName("Mike"));

    @Test
    void check() {
        assertFalse(limit.check(ORDER3, checkedOrdersStorage));
        assertTrue(limit.check(ORDER1, checkedOrdersStorage));
        checkedOrdersStorage.addOrder(ORDER1);
        assertFalse(limit.check(ORDER2, checkedOrdersStorage));
    }

    @Test
    void minPositionGreaterThanMaxPosition() {
        assertThrows(IllegalArgumentException.class, () -> new SymbolPositionPerUserLimit(100, 50, "ETSY", userStorage.getByName("Mike")));
    }
}