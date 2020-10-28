package org.example.limitchecker;

import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.User;
import org.example.limitchecker.model.limit.*;
import org.example.limitchecker.util.OrdersGenerator;
import org.example.limitchecker.util.StockUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        ArrayBlockingQueue<Order> orderQueue = new ArrayBlockingQueue<>(50);

        OrdersGenerator.writeToFile();
        List<Order> orderList = OrdersGenerator.getFromFile();

        List<Limit> limitList = new ArrayList<>();
        limitList.add(new LotsInOrderLimit(70));
        limitList.add(new SymbolPositionLimit(-500, 500));
        limitList.add(new SymbolPositionPerUserLimit(-300, 300));
        limitList.add(new UserOrdersLimit(50));
        limitList.add(new UserOrdersPerSymbolLimit(10));

        Trader trader1 = new Trader(orderQueue, orderList.subList(0, 500));
        Trader trader2 = new Trader(orderQueue, orderList.subList(500, orderList.size()));

        LimitChecker checker = new LimitChecker(orderQueue, limitList);

        Thread trader1Thread = new Thread(trader1, "Trader1 thread");
        Thread trader2Thread = new Thread(trader2, "Trader2 thread");

        Thread checkerThread = new Thread(checker, "Checker thread");

        long startTime = System.nanoTime();

        trader1Thread.start();
        trader2Thread.start();

        Thread.sleep(10);

        checkerThread.start();
        checkerThread.join();

        long endTime = System.nanoTime();
        long total = TimeUnit.MILLISECONDS.toSeconds(endTime - startTime);
        System.out.println("Time: " + total + " milliseconds");

        System.out.println("-----------Result symbol positions----------");
        StockUtils.getStocks().forEach(e -> System.out.println(e + ": " + PassedOrdersStorage.getSymbolPosition(e)));

        System.out.println("-----------Result passed user orders count----------");
        Arrays.stream(User.values()).forEach(e -> System.out.println(e + ": " + PassedOrdersStorage.getUserOrdersCount(e)));
    }
}