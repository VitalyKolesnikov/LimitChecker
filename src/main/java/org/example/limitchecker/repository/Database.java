package org.example.limitchecker.repository;

import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.Stock;
import org.example.limitchecker.model.User;
import org.example.limitchecker.model.limit.Limit;
import org.example.limitchecker.util.LimitLoader;
import org.example.limitchecker.util.OrderLoader;
import org.example.limitchecker.util.StockLoader;

import java.util.List;

public class Database {

    public static final String ORDERS_PATH = "src/main/resources/orders_1m.ser";
    public static final String STOCKS_PATH = "src/main/resources/stocks.csv";
    public static final String LIMITS_PATH = "src/main/resources/limits/";

    private final OrderLoader orderLoader = new OrderLoader(ORDERS_PATH);
    private final StockLoader stockLoader = new StockLoader(STOCKS_PATH);
    private final LimitLoader limitLoader = new LimitLoader(LIMITS_PATH);
    private final UserStorage userStorage = new UserStorage();

    public List<Order> getOrders(int amount) {
        return orderLoader.loadOrdersFromFile(amount);
    }

    public List<Stock> getStocks() {
        return stockLoader.loadStocks(STOCKS_PATH);
    }

    public List<Limit> getLimits() {
        return limitLoader.loadLimits();
    }

    public List<User> getUserList() {
        return userStorage.getUserList();
    }
}