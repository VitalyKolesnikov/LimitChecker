package org.example.limitchecker;

import org.example.limitchecker.repository.CheckedOrdersStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.example.limitchecker.TestData.DB;
import static org.example.limitchecker.TestData.ORDER_LIST;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TraderTest {

    Trader trader;
    LimitChecker checker;

    @BeforeEach
    void setUp() {
        checker = new LimitChecker(DB.getLimits(), new CheckedOrdersStorage());
    }

    @Test
    void run() throws InterruptedException {
        assertEquals(0, checker.getActiveTradersCount());
        trader = new Trader(checker, ORDER_LIST);
        assertEquals(1, checker.getActiveTradersCount());
        Thread traderThread = new Thread(trader);
        traderThread.start();
        traderThread.join();
        assertEquals(0, checker.getActiveTradersCount());
    }
}