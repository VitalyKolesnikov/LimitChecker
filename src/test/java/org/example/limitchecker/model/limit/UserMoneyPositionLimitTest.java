package org.example.limitchecker.model.limit;

import org.junit.jupiter.api.Test;

import static org.example.limitchecker.TestData.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserMoneyPositionLimitTest extends AbstractLimitTest {

    Limit limit = new UserMoneyPositionLimit(-5000, 5000, userStorage.getByName("Mike"));

    @Test
    void check() {
        assertFalse(limit.check(ORDER3, checkedOrdersStorage));
        assertTrue(limit.check(ORDER1, checkedOrdersStorage));
        checkedOrdersStorage.addOrder(ORDER1);
        assertFalse(limit.check(ORDER2, checkedOrdersStorage));
    }
}