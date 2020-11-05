package org.example.limitchecker;

import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.User;
import org.example.limitchecker.model.limit.Limit;
import org.example.limitchecker.repository.CheckedOrdersStorage;
import org.example.limitchecker.repository.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static final int ORDERS_NUM = 1_000_000;
    public static final int TRADERS_NUM = 50;
    public static final int CHECKERS_NUM = 5;
    public static final int QUEUE_SIZE = 5000;

    public static void main(String[] args) throws InterruptedException {

        Database db = new Database();
        List<Order> orderList = db.getOrders(ORDERS_NUM);
        List<Limit> limitList = db.getLimits();
        BlockingQueue<Order> orderQueue = new ArrayBlockingQueue<>(QUEUE_SIZE);
        CheckedOrdersStorage storage = new CheckedOrdersStorage();
        AtomicInteger activeTraders = new AtomicInteger(TRADERS_NUM);

        long startTime = System.nanoTime();

        Trader trader;
        int ordersPerTrader = orderList.size() / TRADERS_NUM;

        Thread traderThread;
        for (int i = 0; i < TRADERS_NUM; i++) {
            int firstOrder = i * ordersPerTrader;
            int lastOrder = (i + 1) * ordersPerTrader;
            trader = new Trader(new QueueProxy<>(orderQueue), orderList.subList(firstOrder, lastOrder), activeTraders);
            traderThread = new Thread(trader, "Trader" + i);
            traderThread.start();
        }

        Thread checkerThread;
        for (int i = 0; i < CHECKERS_NUM; i++) {
            checkerThread = new Thread(new LimitChecker(orderQueue, limitList, storage, activeTraders), "Checker" + i);
            checkerThread.start();
            checkerThread.join();
        }

        long endTime = System.nanoTime();
        long total = TimeUnit.MILLISECONDS.toSeconds(endTime - startTime);

        log.info("---------------------------");
        log.info("All orders has been checked");
        log.info("-----------Result stock positions----------");
        db.getStocks().forEach(e -> log.info("{}: {}", e.getSymbol(), storage.getSymbolPosition(e.getSymbol())
        ));

        log.info("-----------User passed orders / money position----------");
        Arrays.stream(User.values()).forEach(e ->
                log.info("{}: {} / {} $", e, storage.getUserPassedOrdersCount(e), (int) storage.getUserMoneyPosition(e)));

        log.info("---------------------------------------");
        log.info("Time: {} milliseconds", total);
        log.info("Orders passed: {}/{}", storage.getPassedOrdersCount(), orderList.size());
    }
}