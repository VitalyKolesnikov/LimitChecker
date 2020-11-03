package org.example.limitchecker;

import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.User;
import org.example.limitchecker.model.limit.Limit;
import org.example.limitchecker.repository.ProcessedOrdersStorage;
import org.example.limitchecker.util.LimitUtils;
import org.example.limitchecker.util.OrdersGenerator;
import org.example.limitchecker.util.StockUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static final int TRADERS_NUM = 10;
    public static final int CHECKERS_NUM = 1;
    public static final int QUEUE_SIZE = 10000;

    public static void main(String[] args) throws InterruptedException {

        List<Order> orderList = OrdersGenerator.getOrdersFromFile(1_000);
        List<Limit> limitList = LimitUtils.loadLimits();
        BlockingQueue<Order> orderQueue = new ArrayBlockingQueue<>(QUEUE_SIZE);
        ProcessedOrdersStorage storage = new ProcessedOrdersStorage();

        long startTime = System.nanoTime();

        Trader trader;
        int ordersPerTrader = orderList.size()/TRADERS_NUM;

        for (int i = 0; i < TRADERS_NUM; i++) {
            int firstOrder = i * ordersPerTrader;
            int lastOrder = (i + 1) * ordersPerTrader;
            trader = new Trader(orderQueue, orderList.subList(firstOrder, lastOrder));
            new Thread(trader, "Trader" + i).start();
        }

        Thread checkerThread;
        for (int i = 0; i < CHECKERS_NUM; i++) {
            checkerThread = new Thread(new LimitChecker(orderQueue, limitList, storage), "Checker" + i);
            checkerThread.start();
            checkerThread.join();
        }

        long endTime = System.nanoTime();
        long total = TimeUnit.MILLISECONDS.toSeconds(endTime - startTime);

        log.info("-----------Result stock positions----------");
        StockUtils.getStocks().forEach(e -> log.info("{}: {}", e.getSymbol(), storage.getSymbolPosition(e.getSymbol())
        ));

        log.info("-----------User passed orders / money position----------");
        Arrays.stream(User.values()).forEach(e ->
                log.info("{}: {} / {} $", e, storage.getUserPassedOrdersCount(e), (int) storage.getUserMoneyPosition(e)));

        log.info("---------------------------------------");
        log.info("Time: {} milliseconds", total);
        log.info("Orders passed: {}/{}", storage.getPassedOrdersCount(), orderList.size());
    }
}