package org.example.limitchecker;

import org.example.limitchecker.model.Order;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class Trader implements Runnable {

    private final ArrayBlockingQueue<Order> queue;
    private final List<Order> orderList;

    public Trader(ArrayBlockingQueue<Order> queue, List<Order> orderList) {
        this.queue = queue;
        this.orderList = orderList;
    }

    public void placeOrder(Order order) throws InterruptedException {
        queue.put(order);
        System.out.println(Thread.currentThread().getName() + " placed order: " + order);
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
        System.out.println(Thread.currentThread().getName() + " has placed all orders");
    }
}