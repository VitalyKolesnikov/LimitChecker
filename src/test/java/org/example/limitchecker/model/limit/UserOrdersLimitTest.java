package org.example.limitchecker.model.limit;

import org.junit.jupiter.api.Test;

import static org.example.limitchecker.TestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserOrdersLimitTest extends AbstractLimitTest {

    Limit limit = new UserOrdersLimit(2, USER_MIKE);

    @Test
    void check() {
        assertTrue(limit.check(ORDER1, checkedOrdersStorage));
        checkedOrdersStorage.addOrder(ORDER1);
        assertTrue(limit.check(ORDER2, checkedOrdersStorage));
        checkedOrdersStorage.addOrder(ORDER2);
        assertFalse(limit.check(ORDER6, checkedOrdersStorage));
    }

    @Test
    void negativeMaxOrders() {
        assertThrows(IllegalArgumentException.class, () -> new UserOrdersLimit(-5, USER_JOHN));
    }
}