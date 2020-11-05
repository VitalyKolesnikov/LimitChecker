package org.example.limitchecker.repository;

import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.Side;
import org.example.limitchecker.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CheckedOrdersStorage {

    private final AtomicInteger passedOrdersCount = new AtomicInteger(0);
    private final Map<String, Integer> symbolPositionStorage = new ConcurrentHashMap<>();
    private final Map<User, Integer> userPassedOrdersCountStorage = new ConcurrentHashMap<>();
    private final Map<User, Double> userMoneyPositionStorage = new ConcurrentHashMap<>();
    private final Map<User, HashMap<String, Integer>> symbolPositionPerUserStorage = new ConcurrentHashMap<>();
    private final Map<User, HashMap<String, Integer>> userOrdersPerSymbolStorage = new ConcurrentHashMap<>();

    {
        for (User user : User.values()) {
            symbolPositionPerUserStorage.put(user, new HashMap<>());
            userOrdersPerSymbolStorage.put(user, new HashMap<>());
        }
    }

    public void addOrder(Order order) {
        passedOrdersCount.incrementAndGet();
        symbolPositionStorage.merge(order.getStock().getSymbol(), computePositionChange(order), Integer::sum);
        userPassedOrdersCountStorage.merge(order.getUser(), 1, Integer::sum);
        userMoneyPositionStorage.merge(order.getUser(), computeMoneyPositionChange(order), Double::sum);
        symbolPositionPerUserStorage.get(order.getUser())
                .merge(order.getStock().getSymbol(), computePositionChange(order), Integer::sum);
        userOrdersPerSymbolStorage.get(order.getUser())
                .merge(order.getStock().getSymbol(), 1, Integer::sum);
    }

    public int computePositionChange(Order order) {
        return order.getSide().equals(Side.BUY) ? order.getLotCount() : -(order.getLotCount());
    }

    public double computeMoneyPositionChange(Order order) {
        double price = order.getPrice() == null ? order.getStock().getPrice() : order.getPrice();
        double sum = order.getLotCount() * price;
        return order.getSide().equals(Side.BUY) ? sum : -(sum);
    }

    public int getPassedOrdersCount() {
        return passedOrdersCount.get();
    }

    public int getUserPassedOrdersCount(User user) {
        return userPassedOrdersCountStorage.getOrDefault(user, 0);
    }

    public double getUserMoneyPosition(User user) {
        return userMoneyPositionStorage.getOrDefault(user, 0.0);
    }

    public int getSymbolPosition(String symbol) {
        return symbolPositionStorage.getOrDefault(symbol, 0);
    }

    public int getSymbolPositionPerUser(String symbol, User user) {
        return symbolPositionPerUserStorage.get(user).getOrDefault(symbol, 0);
    }

    public int getUserOrdersPerSymbolCount(User user, String symbol) {
        return userOrdersPerSymbolStorage.get(user).getOrDefault(symbol, 0);
    }

}