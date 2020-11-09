package org.example.limitchecker;

import org.example.limitchecker.model.limit.Limit;
import org.example.limitchecker.repository.CheckedOrdersStorage;
import org.example.limitchecker.repository.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.example.limitchecker.TestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LimitCheckerTest {

    CheckedOrdersStorage storage;
    LimitChecker checker;

    @BeforeEach
    void setUp() {
        List<Limit> limitList = DB.getLimits();
        storage = new CheckedOrdersStorage();
        checker = new LimitChecker(limitList, storage);
    }

    @Test
    void takeFromEmptyQueue() throws InterruptedException {
        new Trader(checker, ORDER_LIST);
        Thread checkerThread = new Thread(checker);
        checkerThread.start();
        Thread.sleep(10);
        assertEquals(Thread.State.WAITING, checkerThread.getState());
    }

    @Test
    void addPassedOrderToStorage() throws InterruptedException {
        checker.put(ORDER1);
        checker.checkOrder();
        assertEquals(1, storage.getPassedOrdersCount());
    }

    @Test
    void registerAndDeregisterTrader() throws InterruptedException {
        assertEquals(0, checker.getActiveTradersCount());
        Trader trader1 = new Trader(checker, new ArrayList<>());
        assertEquals(1, checker.getActiveTradersCount());
        Thread thread1 = new Thread(trader1);
        thread1.start();
        thread1.join();
        assertEquals(0, checker.getActiveTradersCount());
    }
}