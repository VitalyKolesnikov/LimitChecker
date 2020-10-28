package org.example.limitchecker;

import org.example.limitchecker.model.Order;

import java.util.concurrent.ArrayBlockingQueue;

public class LimitChecker implements Runnable {

    private final ArrayBlockingQueue<Order> queue;

    public LimitChecker(ArrayBlockingQueue<Order> queue) {
        this.queue = queue;
    }

    public void checkOrder() throws InterruptedException {
        Order order = queue.take();
        System.out.println("Order " + order.getShortInfo() + " has been checked");
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