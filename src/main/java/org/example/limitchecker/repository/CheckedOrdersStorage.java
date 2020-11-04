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
            getSymbolPositionPerUserStorage().put(user, new HashMap<>());
            getUserOrdersPerSymbolStorage().put(user, new HashMap<>());
        }
    }

    public void addOrder(Order order) {
        passedOrdersCount.incrementAndGet();
        symbolPositionStorage.merge(order.getStock().getSymbol(), computePositionChange(order), (k, v) -> v += computePositionChange(order));
        userPassedOrdersCountStorage.merge(order.getUser(), 1, Integer::sum);
        userMoneyPositionStorage.merge(order.getUser(), computeMoneyPositionChange(order), (k, v) -> v += computeMoneyPositionChange(order));
        symbolPositionPerUserStorage.get(order.getUser())
                .merge(order.getStock().getSymbol(), computePositionChange(order), (k, v) -> v += computePositionChange(order));
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

    public AtomicInteger getPassedOrdersCount() {
        return passedOrdersCount;
    }

    public int getUserPassedOrdersCount(User user) {
        return userPassedOrdersCountStorage.getOrDefault(user, 0);
    }


    public Map<User, HashMap<String, Integer>> getSymbolPositionPerUserStorage() {
        return symbolPositionPerUserStorage;
    }

    public Map<User, HashMap<String, Integer>> getUserOrdersPerSymbolStorage() {
        return userOrdersPerSymbolStorage;
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