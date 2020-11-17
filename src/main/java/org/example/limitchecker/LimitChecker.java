package org.example.limitchecker;

import org.example.limitchecker.model.CheckResult;
import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.OrderTask;
import org.example.limitchecker.model.limit.Limit;
import org.example.limitchecker.repository.CheckedOrdersStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class LimitChecker implements Callable<Integer>, QueueProxy {

    private static final Logger log = LoggerFactory.getLogger(LimitChecker.class);
    public static final int QUEUE_SIZE = 500;

    private final BlockingQueue<OrderTask> orderQueue = new ArrayBlockingQueue<>(QUEUE_SIZE);
    private final List<Limit> limits;
    private final CheckedOrdersStorage storage;
    private final Map<Trader, CheckResult> results = new HashMap<>();

    public LimitChecker(List<Limit> limits, CheckedOrdersStorage storage) {
        if (limits.isEmpty()) {
            log.warn("Empty limit list");
        }
        this.limits = limits;
        this.storage = storage;
    }

    public boolean checkOrder() throws InterruptedException {
        OrderTask orderTask = orderQueue.poll(10, TimeUnit.MILLISECONDS);
        if (orderTask == null) {
            return false;
        }
        Trader trader = orderTask.getTrader();
        Order order = orderTask.getOrder();
        synchronized (trader) {
            for (Limit limit : limits) {
                if (!limit.check(order, storage)) {
                    results.put(trader, new CheckResult(order, false));
                    log.info("Order {} status: __REJECT__ ({} violation)", order, limit.getClass().getSimpleName());
                    trader.notify();
                    return false;
                }
            }
            results.put(trader, new CheckResult(order, true));
            log.info("Order {} status: __PASS__", order);
            storage.addOrder(order);
            trader.notify();
            return true;
        }
    }

    @Override
    public Integer call() {
        int passedOrdersCount = 0;
        while (results.size() != 0 || !orderQueue.isEmpty()) {
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
    public void registerTrader(Trader trader) {
        results.put(trader, null);
    }

    @Override
    public void deregisterTrader(Trader trader) {
        results.remove(trader);
    }

    @Override
    public CheckResult getResult(Trader trader) {
        return results.get(trader);
    }

}