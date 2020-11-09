package org.example.limitchecker;

import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.User;
import org.example.limitchecker.model.limit.Limit;
import org.example.limitchecker.repository.CheckedOrdersStorage;
import org.example.limitchecker.repository.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static final int ORDERS_NUM = 100_000;
    public static final int TRADERS_NUM = 25;

    public static void main(String[] args) throws InterruptedException {

        Database db = new Database();
        List<Order> orderList = db.getOrders(ORDERS_NUM);
        List<Limit> limitList = db.getLimits();
        List<User> userList = db.getUserList();

        CheckedOrdersStorage storage = new CheckedOrdersStorage();
        LimitChecker checker = new LimitChecker(limitList, storage);

        Trader trader;
        int ordersPerTrader = orderList.size() / TRADERS_NUM;
        ExecutorService traderExecutor = Executors.newFixedThreadPool(TRADERS_NUM);

        long startTime = System.nanoTime();

        for (int i = 0; i < TRADERS_NUM; i++) {
            int firstOrder = i * ordersPerTrader;
            int lastOrder = (i + 1) * ordersPerTrader;
            trader = new Trader(checker, orderList.subList(firstOrder, lastOrder));
            traderExecutor.submit(trader);
        }

        traderExecutor.shutdown();

        Thread checkerThread = new Thread(checker, "LimitChecker");
        checkerThread.start();
        checkerThread.join();

        long endTime = System.nanoTime();
        long total = TimeUnit.MILLISECONDS.toSeconds(endTime - startTime);

        log.info("---------------------------");
        log.info("All orders has been checked");

        log.info("-----------Result stock positions----------");
        db.getStocks().forEach(e -> log.info("{}: {}", e.getSymbol(), storage.getSymbolPosition(e.getSymbol())));

        log.info("-----------User passed orders / money position----------");
        userList.forEach(e -> log.info("{}: {} / {}", e.getName(), storage.getUserPassedOrdersCount(e),
                storage.getUserMoneyPosition(e) == null ? null : storage.getUserMoneyPosition(e).intValue() + " $"));

        log.info("---------------------------------------");
        log.info("Time: {} milliseconds", total);
        log.info("Orders passed: {}/{}", storage.getPassedOrdersCount(), orderList.size());
    }
}