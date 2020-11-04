package org.example.limitchecker;

import org.example.limitchecker.exception.NoLimitsException;
import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.Side;
import org.example.limitchecker.model.Stock;
import org.example.limitchecker.model.User;
import org.example.limitchecker.model.limit.Limit;
import org.example.limitchecker.repository.CheckedOrdersStorage;
import org.example.limitchecker.repository.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LimitCheckerTest {

    private BlockingQueue<Order> orderQueue;
    private CheckedOrdersStorage storage;
    private AtomicInteger workingTraders;
    private LimitChecker checker;

    @BeforeEach
    public void setUp() {
        Database db = new Database();
        orderQueue = new ArrayBlockingQueue<>(50);
        List<Limit> limitList = db.getLimits();
        storage = new CheckedOrdersStorage();
        workingTraders = new AtomicInteger(10);
        checker = new LimitChecker(orderQueue, limitList, storage, workingTraders);
    }

    @Test
    public void createWithNoLimits() {
        assertThrows(NoLimitsException.class, () -> new LimitChecker(orderQueue, new ArrayList<>(), storage, workingTraders));
    }

    @Test
    public void takeFromEmptyQueue() throws InterruptedException {
        Thread checkerThread = new Thread(() -> assertThrows(InterruptedException.class, () -> checker.checkOrder()));
        checkerThread.start();
        Thread.sleep(10);
        assertEquals(Thread.State.WAITING, checkerThread.getState());
    }

    @Test
    public void addPassedOrderToStorage() throws InterruptedException {
        Order order = new Order(1, LocalTime.now(), User.MIKE, new Stock("ETSY", 112), 15, Side.BUY, 111.5);
        orderQueue.put(order);
        checker.checkOrder();
        assertEquals(storage.getPassedOrdersCount().get(), 1);
    }

}