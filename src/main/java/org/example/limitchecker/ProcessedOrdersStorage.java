package org.example.limitchecker;

import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class ProcessedOrdersStorage {
    private static final List<Order> orderList = new ArrayList<>();
    private static final Map<String, Integer> symbolPositionStorage = new ConcurrentHashMap<>();
    private static final Map<User, Integer> userPassedOrdersCountStorage = new ConcurrentHashMap<>();
    private static final Map<User, Double> userMoneyPositionStorage = new ConcurrentHashMap<>();

    public static List<Order> getOrderList() {
        return orderList;
    }

    public static Map<String, Integer> getSymbolPositionStorage() {
        return symbolPositionStorage;
    }

    public static Map<User, Integer> getUserPassedOrdersCountStorage() {
        return userPassedOrdersCountStorage;
    }

    public static int getUserPassedOrdersCount(User user) {
        return userPassedOrdersCountStorage.getOrDefault(user, 0);
    }

    public static Map<User, Double> getUserMoneyPositionStorage() {
        return userMoneyPositionStorage;
    }

    public static double getUserMoneyPosition(User user) {
        return userMoneyPositionStorage.getOrDefault(user, 0.0);
    }

    public static Stream<Order> getOrdersBySymbol(String symbol) {
        return orderList.stream()
                .filter(order -> order.getStock().getSymbol().equals(symbol));
    }

    public static Stream<Order> getOrdersBySymbolAndUser(String symbol, User user) {
        return getOrdersBySymbol(symbol)
                .filter(order -> order.getUser().equals(user));
    }

    public static int getSymbolPosition(String symbol) {
        return symbolPositionStorage.getOrDefault(symbol, 0);
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

    public static int getUserOrdersPerSymbolCount(User user, String symbol) {
        return (int) getUserOrders(user)
                .filter(order -> order.getStock().getSymbol().equals(symbol))
                .count();
    }

}