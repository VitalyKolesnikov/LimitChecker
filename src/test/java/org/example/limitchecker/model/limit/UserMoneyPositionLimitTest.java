package org.example.limitchecker.model.limit;

import org.example.limitchecker.model.User;
import org.junit.jupiter.api.Test;

import static org.example.limitchecker.TestData.*;
import static org.junit.jupiter.api.Assertions.*;

class UserMoneyPositionLimitTest extends AbstractLimitTest {

    Limit limit = new UserMoneyPositionLimit(-5000, 5000, User.MIKE);

    @Test
    void check() {
        assertFalse(limit.check(ORDER3, storage));
        assertTrue(limit.check(ORDER1, storage));
        storage.addOrder(ORDER1);
        assertFalse(limit.check(ORDER2, storage));
    }
}