package org.example.limitchecker;

import org.example.limitchecker.exception.NoLimitsException;
import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.limit.Limit;
import org.example.limitchecker.repository.CheckedOrdersStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class LimitChecker implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(LimitChecker.class);

    private final BlockingQueue<Order> queue;
    private final List<Limit> limits;
    private final CheckedOrdersStorage storage;
    private final AtomicInteger activeTraders;

    public LimitChecker(BlockingQueue<Order> queue, List<Limit> limits, CheckedOrdersStorage storage, AtomicInteger activeTraders) {
        this.queue = queue;
        if (limits.isEmpty()) {
            throw new NoLimitsException();
        }
        this.limits = limits;
        this.storage = storage;
        this.activeTraders = activeTraders;
    }

    public void checkOrder() throws InterruptedException {
        Order order = queue.take();
        for (Limit limit : limits) {
            if (!limit.check(order, storage)) {
                log.info("Order {} status: __REJECT__ ({} violation) - {}", order, limit.getClass().getSimpleName(), Thread.currentThread().getName());
                return;
            }
        }
        log.info("Order {} status: __PASS__ - {}", order, Thread.currentThread().getName());
        storage.addOrder(order);
    }

    @Override
    public void run() {
        while (activeTraders.get() != 0 || !queue.isEmpty()) {
            try {
                checkOrder();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}