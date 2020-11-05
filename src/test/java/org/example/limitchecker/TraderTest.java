package org.example.limitchecker;

import org.example.limitchecker.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static org.example.limitchecker.TestData.ORDER1;
import static org.example.limitchecker.TestData.ORDER_LIST;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TraderTest {

    BlockingQueue<Order> innerQueue = new ArrayBlockingQueue<>(5);
    QueueProxy<Order> queue = new QueueProxy<>(innerQueue);
    AtomicInteger workingTraders;
    Trader trader;

    @BeforeEach
    void setUp() {
        workingTraders = new AtomicInteger(10);
        trader = new Trader(queue, ORDER_LIST, workingTraders);
    }

    @Test
    void placeOrder() throws InterruptedException {
        assertEquals(0, innerQueue.size());
        trader.placeOrder(ORDER1);
        assertEquals(1, innerQueue.size());
    }

    @Test
    void run() throws InterruptedException {
        Thread traderThread = new Thread(trader);
        traderThread.start();
        traderThread.join();
        assertEquals(3, innerQueue.size());
        assertEquals(9, workingTraders.get());
    }
}