package org.example.limitchecker.model.limit;

import org.example.limitchecker.model.User;
import org.junit.jupiter.api.Test;

import static org.example.limitchecker.TestData.ORDER1;
import static org.example.limitchecker.TestData.ORDER2;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LotsInOrderPerUserPerSymbolLimitTest extends LotsInOrderPerUserLimitTest {

    Limit limit = new LotsInOrderPerUserPerSymbolLimit(45, User.MIKE, "ETSY");

    @Test
    void check() {
        assertTrue(limit.check(ORDER1, storage));
        assertFalse(limit.check(ORDER2, storage));
    }
}