package org.example.limitchecker.model.limit;

import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.User;
import org.example.limitchecker.repository.CheckedOrdersStorage;

public class LotsInOrderPerUserLimit extends LotsInOrderLimit {

    protected User user;

    public LotsInOrderPerUserLimit(int maxLots, User user) {
        super(maxLots);
        this.user = user;
    }

    @Override
    public boolean check(Order order, CheckedOrdersStorage storage) {
        if (!order.getUser().equals(user)) return true;
        return order.getLotCount() <= maxLots;
    }
}