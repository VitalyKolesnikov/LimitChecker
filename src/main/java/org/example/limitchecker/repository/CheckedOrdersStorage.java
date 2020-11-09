package org.example.limitchecker.repository;

import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.Side;
import org.example.limitchecker.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CheckedOrdersStorage {

    private static final Logger log = LoggerFactory.getLogger(CheckedOrdersStorage.class);

    private final AtomicInteger passedOrdersCount = new AtomicInteger(0);
    private final Map<String, Integer> symbolPositionStorage = new ConcurrentHashMap<>();
    private final Map<User, Integer> userPassedOrdersCountStorage = new ConcurrentHashMap<>();
    private final Map<User, Double> userMoneyPositionStorage = new ConcurrentHashMap<>();
    private final Map<User, Map<String, Integer>> symbolPositionPerUserStorage = new ConcurrentHashMap<>();
    private final Map<User, Map<String, Integer>> userOrdersPerSymbolStorage = new ConcurrentHashMap<>();

    public void addOrder(Order order) {
        if (order == null) {
            log.warn("Can`t add null order to the storage!");
            return;
        }

        passedOrdersCount.incrementAndGet();
        symbolPositionStorage.merge(order.getStock().getSymbol(), computePositionChange(order), Integer::sum);
        userPassedOrdersCountStorage.merge(order.getUser(), 1, Integer::sum);
        userMoneyPositionStorage.merge(order.getUser(), computeMoneyPositionChange(order), Double::sum);

        symbolPositionPerUserStorage.computeIfAbsent(order.getUser(), v -> new ConcurrentHashMap<>());
        symbolPositionPerUserStorage.get(order.getUser()).merge(order.getStock().getSymbol(), computePositionChange(order), Integer::sum);

        userOrdersPerSymbolStorage.computeIfAbsent(order.getUser(), v -> new ConcurrentHashMap<>());
        userOrdersPerSymbolStorage.get(order.getUser()).merge(order.getStock().getSymbol(), 1, Integer::sum);
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

    public Double getUserMoneyPosition(User user) {
        return user == null ? null : userMoneyPositionStorage.get(user);
    }

    public Integer getSymbolPosition(String symbol) {
        return symbolPositionStorage.get(symbol);
    }

    public Integer getSymbolPositionPerUser(String symbol, User user) {
        if (symbol == null || user == null) return null;
        Map<String, Integer> userSymbolPositionMap = symbolPositionPerUserStorage.get(user);
        return userSymbolPositionMap == null ? null : userSymbolPositionMap.get(symbol);
    }

    public int getUserOrdersPerSymbolCount(User user, String symbol) {
        Map<String, Integer> userSymbolPositionMap = userOrdersPerSymbolStorage.get(user);
        return userSymbolPositionMap == null ? 0 : userSymbolPositionMap.getOrDefault(symbol, 0);
    }

}