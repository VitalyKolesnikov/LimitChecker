package org.example.limitchecker.model.limit;

import org.junit.jupiter.api.Test;

import static org.example.limitchecker.TestData.ORDER1;
import static org.example.limitchecker.TestData.ORDER2;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LotsInOrderPerUserPerSymbolLimitTest extends LotsInOrderPerUserLimitTest {

    Limit limit = new LotsInOrderPerUserPerSymbolLimit(45, userStorage.getByName("Mike"), "ETSY");

    @Test
    void check() {
        assertTrue(limit.check(ORDER1, checkedOrdersStorage));
        assertFalse(limit.check(ORDER2, checkedOrdersStorage));
    }
}