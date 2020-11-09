package org.example.limitchecker;

import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.limit.Limit;
import org.example.limitchecker.repository.CheckedOrdersStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class LimitChecker implements Runnable, QueueProxy {

    private static final Logger log = LoggerFactory.getLogger(LimitChecker.class);
    public static final int QUEUE_SIZE = 500;

    private final BlockingQueue<Order> queue = new ArrayBlockingQueue<>(QUEUE_SIZE);
    private final List<Limit> limits;
    private final CheckedOrdersStorage storage;
    private final AtomicInteger activeTradersCount = new AtomicInteger(0);

    public LimitChecker(List<Limit> limits, CheckedOrdersStorage storage) {
        if (limits.isEmpty()) {
            log.warn("Empty limit list");
        }
        this.limits = limits;
        this.storage = storage;
    }

    public void checkOrder() throws InterruptedException {
        log.info("Queue size: " + queue.size());
        Order order = queue.take();
        for (Limit limit : limits) {
            if (!limit.check(order, storage)) {
                log.info("Order {} status: __REJECT__ ({} violation) - {}",
                        order, limit.getClass().getSimpleName(), Thread.currentThread().getName());
                return;
            }
        }
        log.info("Order {} status: __PASS__ - {}", order, Thread.currentThread().getName());
        storage.addOrder(order);
    }

    @Override
    public void run() {
        while (activeTradersCount.get() != 0 || !queue.isEmpty()) {
            try {
                checkOrder();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void put(Order order) throws InterruptedException {
        queue.put(order);
    }

    @Override
    public void registerTrader() {
        activeTradersCount.incrementAndGet();
    }

    @Override
    public void deregisterTrader() {
        activeTradersCount.decrementAndGet();
    }

    public int getActiveTradersCount() {
        return activeTradersCount.get();
    }
}