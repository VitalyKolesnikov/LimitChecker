package org.example.limitchecker;

import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.limit.Limit;
import org.example.limitchecker.repository.ProcessedOrdersStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class LimitChecker implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(LimitChecker.class);

    private final BlockingQueue<Order> queue;
    private final List<Limit> limits;
    private final ProcessedOrdersStorage storage;
//    private final CountDownLatch latch;

    public LimitChecker(BlockingQueue<Order> queue, List<Limit> limits, ProcessedOrdersStorage storage) {
        this.queue = queue;
        this.limits = limits;
        this.storage = storage;
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
        while (true) {
            try {
                checkOrder();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        log.info("---------------------------");
//        log.info("All orders has been checked");
    }
}