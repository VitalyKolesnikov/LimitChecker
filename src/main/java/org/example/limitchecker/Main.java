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

        ArrayBlockingQueue<Order> orderQueue = new ArrayBlockingQueue<>(500);

        OrdersGenerator.writeToFile();
        List<Order> orderList = OrdersGenerator.getFromFile();

        List<Limit> limitList = new ArrayList<>();

        limitList.add(new LotsInOrderLimit(70));
        limitList.add(new LotsInOrderPerUserLimit(30, User.MIKE));
        limitList.add(new LotsInOrderPerUserPerSymbolLimit(15, User.MIKE, "KIRK"));
        limitList.add(new StockPositionLimit(-150, 150));
        limitList.add(new StockPositionPerUserLimit(-75, 75));
        limitList.add(new UserOrdersLimit(65));
        limitList.add(new UserOrdersPerStockLimit(2));
        limitList.add(new UserMoneyPositionLimit(-5000, 5000));

        Trader trader1 = new Trader(orderQueue, orderList.subList(0, orderList.size()/2));
        Trader trader2 = new Trader(orderQueue, orderList.subList(orderList.size()/2, orderList.size()));

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

        System.out.println("-----------Result stock positions----------");
        StockUtils.getStocks().forEach(e -> System.out.println(e.getSymbol() + ": " + PassedOrdersStorage.getStockPosition(e)));

        System.out.println("-----------Result passed user orders count----------");
        Arrays.stream(User.values()).forEach(e -> System.out.println(e + ": " + PassedOrdersStorage.getUserOrdersCount(e)));

        System.out.println("---------------------------------------");
        System.out.println("Time: " + total + " milliseconds");
        System.out.println("Orders passed: " + PassedOrdersStorage.orderList.size() + "/" + orderList.size());
    }
}