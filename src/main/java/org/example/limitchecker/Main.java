package org.example.limitchecker;

import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.User;
import org.example.limitchecker.model.limit.Limit;
import org.example.limitchecker.util.LimitUtils;
import org.example.limitchecker.util.OrdersGenerator;
import org.example.limitchecker.util.StockUtils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Main {

    public static final int TRADERS_NUM = 10;
    public static final int QUEUE_SIZE = 500;
    public static List<Order> orderList = OrdersGenerator.getOrdersFromFile(1_000_000);
    public static List<Limit> limitList = LimitUtils.loadLimits();
    public static ArrayBlockingQueue<Order> orderQueue = new ArrayBlockingQueue<>(QUEUE_SIZE);

    public static void main(String[] args) throws InterruptedException {

        long startTime = System.nanoTime();

//        for (User user : User.values()) {
//            ProcessedOrdersStorage.getSymbolPositionPerUserStorage().put(user, new ConcurrentHashMap<>());
//        }

        Trader trader;
        int ordersPerTrader = orderList.size()/TRADERS_NUM;

        for (int i = 0; i < TRADERS_NUM; i++) {
            int firstOrder = i * ordersPerTrader;
            int lastOrder = (i + 1) * ordersPerTrader;
            trader = new Trader(orderQueue, orderList.subList(firstOrder, lastOrder));
            new Thread(trader, "Trader" + i).start();
        }

        Thread checkerThread1 = new Thread(new LimitChecker(orderQueue, limitList), "Checker1");
        Thread checkerThread2 = new Thread(new LimitChecker(orderQueue, limitList), "Checker2");
        Thread checkerThread3 = new Thread(new LimitChecker(orderQueue, limitList), "Checker3");
        Thread checkerThread4 = new Thread(new LimitChecker(orderQueue, limitList), "Checker4");

        checkerThread1.start();
        checkerThread2.start();
        checkerThread3.start();
        checkerThread4.start();

        checkerThread1.join();
        checkerThread2.join();
        checkerThread3.join();
        checkerThread4.join();

        long endTime = System.nanoTime();
        long total = TimeUnit.MILLISECONDS.toSeconds(endTime - startTime);

        System.out.println("-----------Result stock positions----------");
        StockUtils.getStocks().forEach(e -> System.out.println(e.getSymbol() + ": " +
                ProcessedOrdersStorage.getSymbolPosition(e.getSymbol())));

        System.out.println("-----------User passed orders / money position----------");
        Arrays.stream(User.values()).forEach(e -> System.out.println(e + ": " +
                ProcessedOrdersStorage.getUserPassedOrdersCount(e) + " / " +
                (int) ProcessedOrdersStorage.getUserMoneyPosition(e) + " $"));

        System.out.println("---------------------------------------");
        System.out.println("Time: " + total + " milliseconds");
        System.out.println("Orders passed: " + ProcessedOrdersStorage.getOrderList().size() + "/" + orderList.size());
    }
}