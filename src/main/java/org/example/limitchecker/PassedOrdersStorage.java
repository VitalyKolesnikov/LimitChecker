package org.example.limitchecker;

import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.Stock;
import org.example.limitchecker.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class PassedOrdersStorage {
    public static List<Order> orderList = new ArrayList<>();

    public static Stream<Order> getOrdersByStock(Stock stock) {
        return orderList.stream()
                .filter(order -> order.getStock().equals(stock));
    }

    public static Stream<Order> getOrdersByStockAndUser(Stock stock, User user) {
        return getOrdersByStock(stock)
                .filter(order -> order.getUser().equals(user));
    }

    public static int getStockPosition(Stock stock) {
        return getOrdersByStock(stock)
                .mapToInt(Order::getPositionChange)
                .sum();
    }

    public static int getStockPositionPerUser(Stock stock, User user) {
        return getOrdersByStockAndUser(stock, user)
                .mapToInt(Order::getPositionChange)
                .sum();
    }

    public static Stream<Order> getUserOrders(User user) {
        return orderList.stream()
                .filter(order -> order.getUser().equals(user));
    }

    public static int getUserOrdersCount(User user) {
        return (int) getUserOrders(user).count();
    }

    public static int getUserOrdersPerStockCount(User user, Stock stock) {
        return (int) getUserOrders(user)
                .filter(order -> order.getStock().equals(stock))
                .count();
    }

    public static double getUserMoneyPosition(User user) {
        return getUserOrders(user)
                .mapToDouble(Order::getMoneyPositionChange)
                .sum();
    }
}