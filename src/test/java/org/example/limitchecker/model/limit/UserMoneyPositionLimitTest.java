package org.example.limitchecker.model.limit;

import org.junit.jupiter.api.Test;

import static org.example.limitchecker.TestData.*;
import static org.junit.jupiter.api.Assertions.*;

class UserMoneyPositionLimitTest extends AbstractLimitTest {

    Limit limit1 = new UserMoneyPositionLimit(-5000, 5000, userStorage.getByName("Mike"));
    Limit limit2 = new UserMoneyPositionLimit(-1500, 1500, userStorage.getByName("John"));

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
        assertThrows(IllegalArgumentException.class, () -> new UserMoneyPositionLimit(1000, 500, userStorage.getByName("Mike")));
    }
}