package org.example.limitchecker;

import org.example.limitchecker.exception.NoLimitsException;
import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.limit.Limit;
import org.example.limitchecker.repository.CheckedOrdersStorage;
import org.example.limitchecker.repository.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static org.example.limitchecker.TestData.ORDER1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LimitCheckerTest {

    BlockingQueue<Order> orderQueue;
    CheckedOrdersStorage storage;
    AtomicInteger workingTraders;
    LimitChecker checker;

    @BeforeEach
    void setUp() {
        Database db = new Database();
        orderQueue = new ArrayBlockingQueue<>(50);
        List<Limit> limitList = db.getLimits();
        storage = new CheckedOrdersStorage();
        workingTraders = new AtomicInteger(10);
        checker = new LimitChecker(orderQueue, limitList, storage, workingTraders);
    }

    @Test
    void createWithNoLimits() {
        assertThrows(NoLimitsException.class, () -> new LimitChecker(orderQueue, new ArrayList<>(), storage, workingTraders));
    }

    @Test
    void takeFromEmptyQueue() throws InterruptedException {
        Thread checkerThread = new Thread(() -> assertThrows(InterruptedException.class, () -> checker.checkOrder()));
        checkerThread.start();
        Thread.sleep(10);
        assertEquals(Thread.State.WAITING, checkerThread.getState());
    }

    @Test
    void addPassedOrderToStorage() throws InterruptedException {
        orderQueue.put(ORDER1);
        checker.checkOrder();
        assertEquals(1, storage.getPassedOrdersCount().get());
    }

}