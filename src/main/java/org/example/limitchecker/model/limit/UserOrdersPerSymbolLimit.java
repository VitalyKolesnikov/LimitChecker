package org.example.limitchecker.model.limit;

import org.example.limitchecker.PassedOrdersStorage;
import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.User;

public class UserOrdersPerSymbolLimit extends UserOrdersLimit {

    private final String symbol;

    public UserOrdersPerSymbolLimit(int maxOrders, User user, String symbol) {
        super(maxOrders, user);
        this.symbol = symbol;
    }

    @Override
    public boolean check(Order order) {
        if (!order.getUser().equals(user) || !order.getStock().getSymbol().equals(symbol)) return true;
        return PassedOrdersStorage.getUserOrdersPerStockCount(order.getUser(), order.getStock()) < maxOrders;
    }
}