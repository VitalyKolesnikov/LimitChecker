package org.example.limitchecker.model.limit;

import org.example.limitchecker.repository.ProcessedOrdersStorage;
import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.User;

public class UserOrdersLimit implements Limit {

    protected final int maxOrders;
    protected final User user;

    public UserOrdersLimit(int maxOrders, User user) {
        this.maxOrders = maxOrders;
        this.user = user;
    }

    @Override
    public boolean check(Order order, ProcessedOrdersStorage storage) {
        if (!order.getUser().equals(user)) return true;
        return storage.getUserPassedOrdersCount(order.getUser()) < maxOrders;
    }
}