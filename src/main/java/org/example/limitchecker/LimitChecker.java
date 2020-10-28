package org.example.limitchecker;

import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.limit.Limit;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class LimitChecker implements Runnable {

    private final ArrayBlockingQueue<Order> queue;
    private List<Limit> limits;

    public LimitChecker(ArrayBlockingQueue<Order> queue, List<Limit> limits) {
        this.queue = queue;
        this.limits = limits;
    }

    public void checkOrder() throws InterruptedException {
        Order order = queue.take();

        for (Limit limit : limits) {
            if (!limit.check(order)) {
                System.out.println("Order " + order.getShortInfo() + " status: __REJECT__");
                return;
            }
        }
        System.out.println("Order " + order.getShortInfo() + " status: __PASS__");
    }

    @Override
    public void run() {
        while (!queue.isEmpty()) {
            try {
//                Thread.sleep(1500);
                checkOrder();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("---------------------------");
        System.out.println("All orders has been checked");
    }
}