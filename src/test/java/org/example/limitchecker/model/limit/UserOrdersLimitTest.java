package org.example.limitchecker.model.limit;

import org.junit.jupiter.api.Test;

import static org.example.limitchecker.TestData.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserOrdersLimitTest extends AbstractLimitTest {

    Limit limit = new UserOrdersLimit(2, userStorage.getByName("Mike"));

    @Test
    void check() {
        assertTrue(limit.check(ORDER1, checkedOrdersStorage));
        checkedOrdersStorage.addOrder(ORDER1);
        assertTrue(limit.check(ORDER2, checkedOrdersStorage));
        checkedOrdersStorage.addOrder(ORDER1);
        assertFalse(limit.check(ORDER3, checkedOrdersStorage));
    }
}