package org.example.limitchecker.model.limit;

import org.example.limitchecker.PassedOrdersStorage;
import org.example.limitchecker.model.Order;

public class UserOrdersLimit implements Limit {

    private final int maxOrders;

    public UserOrdersLimit(int maxOrders) {
        this.maxOrders = maxOrders;
    }

    @Override
    public boolean check(Order order) {
        return PassedOrdersStorage.getUserOrdersCount(order.getUser()) < maxOrders;
    }
}
