package org.example.limitchecker.repository;

import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.Stock;
import org.example.limitchecker.model.limit.Limit;
import org.example.limitchecker.util.LimitLoader;
import org.example.limitchecker.util.OrderLoader;
import org.example.limitchecker.util.StockLoader;

import java.util.List;

public class Database {
    private final OrderLoader orderLoader = new OrderLoader();
    private final StockLoader stockLoader = new StockLoader();
    private final LimitLoader limitLoader = new LimitLoader();

    public List<Order> getOrders(int amount) {
        return orderLoader.loadOrdersFromFile(amount);
    }

    public List<Stock> getStocks() {
        return stockLoader.loadStocks();
    }

    public List<Limit> getLimits() {
        return limitLoader.loadLimits();
    }
}
