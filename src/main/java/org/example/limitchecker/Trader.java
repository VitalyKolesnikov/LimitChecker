package org.example.limitchecker;

import org.example.limitchecker.model.CheckResult;
import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.OrderTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Trader implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(Trader.class);

    private final QueueProxy orderQueue;
    private final List<Order> orderList;

    public Trader(QueueProxy orderQueue, List<Order> orderList) {
        this.orderQueue = orderQueue;
        this.orderList = orderList;
        orderQueue.registerTrader(this);
    }

    public void submitOrderTask(Order order) throws InterruptedException {
        orderQueue.submitOrderTask(new OrderTask(this, order));
        log.info("Trader {} placed order: {}", Thread.currentThread().getId(), order);
    }

    @Override
    public void run() {
        for (Order order : orderList) {
            try {
                synchronized (this) {
                    submitOrderTask(order);
                    wait();
                    CheckResult result = orderQueue.getResult(this);
                    log.info("Trader {} retrieved result: order {} - {}",
                            Thread.currentThread().getId(), result.getOrder(), result.isPassed() ? "passed" : "rejected");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        log.info("Trader {} has placed all orders", Thread.currentThread().getId());
        orderQueue.deregisterTrader(this);
    }

}