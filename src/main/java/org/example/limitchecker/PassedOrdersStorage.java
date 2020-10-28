package org.example.limitchecker;

import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class PassedOrdersStorage {
    public static List<Order> orderList = new ArrayList<>();

    public static Stream<Order> getOrdersBySymbol(String symbol) {
        return orderList.stream()
                .filter(order -> order.getSymbol().equals(symbol));
    }

    public static Stream<Order> getOrdersBySymbolAndUser(String symbol, User user) {
        return getOrdersBySymbol(symbol)
                .filter(order -> order.getUser().equals(user));
    }

    public static int getSymbolPosition(String symbol) {
        return getOrdersBySymbol(symbol)
                .mapToInt(Order::getPositionChange)
                .sum();
    }

    public static int getSymbolPositionPerUser(String symbol, User user) {
        return getOrdersBySymbolAndUser(symbol, user)
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

    public static int getUserOrdersPerSymbolCount(User user, String symbol) {
        return (int) getUserOrders(user)
                .filter(order -> order.getSymbol().equals(symbol))
                .count();
    }
}