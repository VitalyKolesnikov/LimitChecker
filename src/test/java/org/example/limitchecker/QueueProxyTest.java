package org.example.limitchecker;

import org.example.limitchecker.model.Order;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static org.example.limitchecker.TestData.ORDER1;
import static org.junit.jupiter.api.Assertions.assertEquals;

class QueueProxyTest {

    BlockingQueue<Order> innerQueue = new ArrayBlockingQueue<>(5);
    QueueProxy<Order> queue = new QueueProxy<>(innerQueue);

    @Test
    void put() throws InterruptedException {
        assertEquals(0, innerQueue.size());
        queue.put(ORDER1);
        assertEquals(1, innerQueue.size());
    }
}