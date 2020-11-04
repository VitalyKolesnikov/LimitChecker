package org.example.limitchecker.model.limit;

import org.example.limitchecker.model.User;
import org.junit.jupiter.api.Test;

import static org.example.limitchecker.TestData.ORDER1;
import static org.example.limitchecker.TestData.ORDER2;
import static org.junit.jupiter.api.Assertions.*;

class LotsInOrderPerUserLimitTest extends LotsInOrderLimitTest {

    Limit limit = new LotsInOrderPerUserLimit(45, User.MIKE);

    @Test
    void check() {
        assertTrue(limit.check(ORDER1, storage));
        assertFalse(limit.check(ORDER2, storage));
    }
}