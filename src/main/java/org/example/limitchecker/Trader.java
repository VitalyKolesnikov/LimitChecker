package org.example.limitchecker;

import org.example.limitchecker.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class Trader implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(Trader.class);

    private final BlockingQueue<Order> queue;
    private final List<Order> orderList;
    private final CountDownLatch latch;

    public Trader(BlockingQueue<Order> queue, List<Order> orderList, CountDownLatch latch) {
        this.queue = queue;
        this.orderList = orderList;
        this.latch = latch;
    }

    public void placeOrder(Order order) throws InterruptedException {
        queue.put(order);
        log.info("{} placed order: {}", Thread.currentThread().getName(), order);
    }

    @Override
    public void run() {
        for (Order order : orderList) {
            try {
                placeOrder(order);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        log.info("{} has placed all orders", Thread.currentThread().getName());
        latch.countDown();
    }
}