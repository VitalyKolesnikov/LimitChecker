package org.example.limitchecker.model.limit;

import org.example.limitchecker.model.User;
import org.junit.jupiter.api.Test;

import static org.example.limitchecker.TestData.*;
import static org.junit.jupiter.api.Assertions.*;

class UserOrdersPerSymbolLimitTest extends UserOrdersLimitTest {

    Limit limit = new UserOrdersPerSymbolLimit(2, User.MIKE, "ETSY");

    @Test
    void testCheck() {
        assertTrue(limit.check(ORDER1, storage));
        storage.addOrder(ORDER1);
        assertTrue(limit.check(ORDER2, storage));
        storage.addOrder(ORDER1);
        assertFalse(limit.check(ORDER3, storage));
    }
}