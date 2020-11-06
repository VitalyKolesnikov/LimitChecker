package org.example.limitchecker;

import org.example.limitchecker.model.Order;

import java.util.concurrent.BlockingQueue;

public class QueueProxyImpl implements QueueProxy {
    private final BlockingQueue<Order> queue;

    public QueueProxyImpl(BlockingQueue<Order> queue) {
        this.queue = queue;
    }

    public void put(Order order) throws InterruptedException {
        queue.put(order);
    }
}