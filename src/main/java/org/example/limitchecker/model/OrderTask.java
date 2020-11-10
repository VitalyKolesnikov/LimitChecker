package org.example.limitchecker.model;

import org.example.limitchecker.Trader;

public class OrderTask {
    private final Trader trader;
    private final Order order;

    public OrderTask(Trader trader, Order order) {
        this.trader = trader;
        this.order = order;
    }

    public Trader getTrader() {
        return trader;
    }

    public Order getOrder() {
        return order;
    }
}
