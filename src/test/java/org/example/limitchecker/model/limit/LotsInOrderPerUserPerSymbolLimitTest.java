package org.example.limitchecker.model.limit;

import org.junit.jupiter.api.Test;

import static org.example.limitchecker.TestData.*;
import static org.junit.jupiter.api.Assertions.*;

class LotsInOrderPerUserPerSymbolLimitTest extends LotsInOrderPerUserLimitTest {

    Limit limit = new LotsInOrderPerUserPerSymbolLimit(45, USER_MIKE, "ETSY");

    @Test
    void check() {
        assertTrue(limit.check(ORDER1, checkedOrdersStorage));
        assertFalse(limit.check(ORDER2, checkedOrdersStorage));
    }

    @Test
    void zeroOrNegativeMaxLots() {
        assertThrows(IllegalArgumentException.class, () ->
                new LotsInOrderPerUserPerSymbolLimit(0, USER_MIKE, "ETSY"));
        assertThrows(IllegalArgumentException.class, () ->
                new LotsInOrderPerUserPerSymbolLimit(-10, USER_MIKE, "ETSY"));
    }
}