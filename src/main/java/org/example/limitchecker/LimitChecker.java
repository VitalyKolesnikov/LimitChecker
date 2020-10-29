package org.example.limitchecker;

import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.limit.Limit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class LimitChecker implements Runnable {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final ArrayBlockingQueue<Order> queue;
    private final List<Limit> limits;

    public LimitChecker(ArrayBlockingQueue<Order> queue, List<Limit> limits) {
        this.queue = queue;
        this.limits = limits;
    }

    public void checkOrder() throws InterruptedException {
        Order order = queue.take();
        for (Limit limit : limits) {
            if (!limit.check(order)) {
                log.info("Order {} status: __REJECT__ ({} violation)", order, limit.getClass().getSimpleName());
                return;
            }
        }
        log.info("Order {} status: __PASS__", order);
        PassedOrdersStorage.orderList.add(order);
    }

    @Override
    public void run() {
        while (!queue.isEmpty()) {
            try {
                checkOrder();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("---------------------------");
        System.out.println("All orders has been checked");
    }
}