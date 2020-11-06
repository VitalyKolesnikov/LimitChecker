package org.example.limitchecker.repository;

import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.Stock;
import org.example.limitchecker.model.User;
import org.example.limitchecker.model.limit.Limit;
import org.example.limitchecker.util.LimitLoader;
import org.example.limitchecker.util.OrderLoader;
import org.example.limitchecker.util.StockLoader;

import java.util.List;

import static org.example.limitchecker.util.OrderGenerator.ORDERS_PATH;
import static org.example.limitchecker.util.StockLoader.STOCKS_PATH;

public class Database {

    private final OrderLoader orderLoader = new OrderLoader();
    private final StockLoader stockLoader = new StockLoader();
    private final LimitLoader limitLoader = new LimitLoader();
    private final UserStorage userStorage = new UserStorage();

    public List<Order> getOrders(int amount) {
        return orderLoader.loadOrdersFromFile(ORDERS_PATH, amount);
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