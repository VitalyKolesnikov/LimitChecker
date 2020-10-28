package org.example.limitchecker;

import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.limit.Limit;
import org.example.limitchecker.model.limit.LotsInOrderLimit;
import org.example.limitchecker.model.limit.SymbolPositionLimit;
import org.example.limitchecker.util.OrdersGenerator;
import org.example.limitchecker.util.StockUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        ArrayBlockingQueue<Order> orderQueue = new ArrayBlockingQueue<>(3);

        OrdersGenerator.writeToFile();
        List<Order> orderList = OrdersGenerator.getFromFile();

        List<Limit> limitList= new ArrayList<>();
        limitList.add(new LotsInOrderLimit(60));
        limitList.add(new SymbolPositionLimit(-40, 40));

        Trader trader1 = new Trader(orderQueue, orderList.subList(0, 5));
        Trader trader2 = new Trader(orderQueue, orderList.subList(5, orderList.size()));

        LimitChecker checker = new LimitChecker(orderQueue, limitList);

        Thread trader1Thread = new Thread(trader1, "Trader1 thread");
        Thread trader2Thread = new Thread(trader2, "Trader2 thread");

        Thread checkerThread = new Thread(checker, "Checker thread");

        trader1Thread.start();
        trader2Thread.start();

        Thread.sleep(50);

        checkerThread.start();

        checkerThread.join();
        System.out.println("---------------------");
        System.out.println("Result symbol positions");
        StockUtils.getStocks().forEach(e -> System.out.println(e + ": " + PassedOrdersStorage.getSymbolPosition(e)));
    }
}