package org.example.limitchecker.model.limit;

import org.junit.jupiter.api.Test;

import static org.example.limitchecker.TestData.*;
import static org.junit.jupiter.api.Assertions.*;

class UserOrdersPerSymbolLimitTest extends UserOrdersLimitTest {

    Limit limit = new UserOrdersPerSymbolLimit(1, userStorage.getByName("Mike"), "ETSY");

    @Test
    void check() {
        assertTrue(limit.check(ORDER1, checkedOrdersStorage));
        checkedOrdersStorage.addOrder(ORDER1);
        assertFalse(limit.check(ORDER2, checkedOrdersStorage));
    }

    @Test
    void zeroOrNegativeMaxOrders() {
        assertThrows(IllegalArgumentException.class, () -> new UserOrdersPerSymbolLimit(-5, userStorage.getByName("Mike"), "ETSY"));
    }
}