package org.example.limitchecker.model.limit;

import org.example.limitchecker.model.User;
import org.junit.jupiter.api.Test;

import static org.example.limitchecker.TestData.*;
import static org.junit.jupiter.api.Assertions.*;

class SymbolPositionPerUserLimitTest extends SymbolPositionLimitTest {

    Limit limit = new SymbolPositionPerUserLimit(-50, 100, "ETSY", User.MIKE);

    @Test
    void testCheck() {
        assertFalse(limit.check(ORDER3, storage));
        assertTrue(limit.check(ORDER1, storage));
        storage.addOrder(ORDER1);
        assertFalse(limit.check(ORDER2, storage));
    }
}