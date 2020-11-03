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
    public static final int CHECKERS_NUM = 10;
    public static final int QUEUE_SIZE = 5000;

    public static void main(String[] args) throws InterruptedException {

        List<Order> orderList = OrdersGenerator.getOrdersFromFile(100_000);
        List<Limit> limitList = LimitUtils.loadLimits();
        BlockingQueue<Order> orderQueue = new ArrayBlockingQueue<>(QUEUE_SIZE);
        ProcessedOrdersStorage storage = new ProcessedOrdersStorage();

//        CountDownLatch latch = new CountDownLatch(TRADERS_NUM);

        long startTime = System.nanoTime();

        Trader trader;
        int ordersPerTrader = orderList.size() / TRADERS_NUM;

        Thread checkerThread;
        for (int i = 0; i < CHECKERS_NUM; i++) {
            checkerThread = new Thread(new LimitChecker(orderQueue, limitList, storage), "Checker" + i);
            checkerThread.setDaemon(true);
            checkerThread.start();
//            checkerThread.join();
        }

        Thread traderThread;
        for (int i = 0; i < TRADERS_NUM; i++) {
            int firstOrder = i * ordersPerTrader;
            int lastOrder = (i + 1) * ordersPerTrader;
            trader = new Trader(orderQueue, orderList.subList(firstOrder, lastOrder));
            traderThread = new Thread(trader, "Trader" + i);
            traderThread.start();
            traderThread.join();
        }

//        latch.await();

        long endTime = System.nanoTime();
        long total = TimeUnit.MILLISECONDS.toSeconds(endTime - startTime);

        Thread.sleep(100);

        log.info("---------------------------");
        log.info("All orders has been checked");
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