package org.example.limitchecker;

import org.example.limitchecker.model.CheckResult;
import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.OrderTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Trader implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(Trader.class);

    private final QueueProxy orderQueue;
    private final List<Order> orderList;
    private final BlockingQueue<CheckResult> resultQueue = new ArrayBlockingQueue<>(1);

    public Trader(QueueProxy orderQueue, List<Order> orderList) {
        this.orderQueue = orderQueue;
        this.orderList = orderList;
        orderQueue.registerTrader();
    }

    public void submitOrderTask(Order order) throws InterruptedException {
        orderQueue.submitOrderTask(new OrderTask(this, order));
        log.info("Trader {} placed order: {}", Thread.currentThread().getId(), order);
    }

    public void submitResult(CheckResult result) throws InterruptedException {
        this.resultQueue.put(result);
    }

    @Override
    public void run() {
        for (Order order : orderList) {
            try {
                submitOrderTask(order);
                CheckResult result = resultQueue.take();
                log.info("Trader {} retrieved result: order {} - {}",
                        Thread.currentThread().getId(), result.getOrder(), result.isPassed() ? "passed" : "rejected");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        log.info("Trader {} has placed all orders", Thread.currentThread().getId());
        orderQueue.deregisterTrader();
    }
}