package org.example.limitchecker;

import org.example.limitchecker.model.CheckResult;
import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.OrderTask;
import org.example.limitchecker.model.limit.Limit;
import org.example.limitchecker.repository.CheckedOrdersStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class LimitChecker implements Callable<Integer>, QueueProxy {

    private static final Logger log = LoggerFactory.getLogger(LimitChecker.class);
    public static final int QUEUE_SIZE = 500;

    private final BlockingQueue<OrderTask> orderQueue = new ArrayBlockingQueue<>(QUEUE_SIZE);
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

    public boolean checkOrder() throws InterruptedException {
        OrderTask orderTask = orderQueue.poll(10, TimeUnit.MILLISECONDS);
        if (orderTask == null) return false;
        Order order = orderTask.getOrder();
        for (Limit limit : limits) {
            if (!limit.check(order, storage)) {
                orderTask.getTrader().submitResult(new CheckResult(order, false));
                log.info("Order {} status: __REJECT__ ({} violation)", order, limit.getClass().getSimpleName());
                return false;
            }
        }
        orderTask.getTrader().submitResult(new CheckResult(order, true));
        log.info("Order {} status: __PASS__", order);
        storage.addOrder(order);
        return true;
    }

    @Override
    public Integer call() {
        int passedOrdersCount = 0;
        while (activeTradersCount.get() != 0 || !orderQueue.isEmpty()) {
            try {
                if (checkOrder()) passedOrdersCount++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return passedOrdersCount;
    }

    @Override
    public void submitOrderTask(OrderTask orderTask) throws InterruptedException {
        orderQueue.put(orderTask);
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