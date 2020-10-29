package org.example.limitchecker.model.limit;

import org.example.limitchecker.PassedOrdersStorage;
import org.example.limitchecker.model.Order;

public class UserOrdersPerStockLimit extends UserOrdersLimit {

    public UserOrdersPerStockLimit(int maxOrders) {
        super(maxOrders);
    }

    @Override
    public boolean check(Order order) {
        return PassedOrdersStorage.getUserOrdersPerStockCount(order.getUser(), order.getStock()) < maxOrders;
    }
}