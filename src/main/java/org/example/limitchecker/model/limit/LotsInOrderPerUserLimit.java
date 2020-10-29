package org.example.limitchecker.model.limit;

import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.User;

public class LotsInOrderPerUserLimit extends LotsInOrderLimit {

    private User user;

    public LotsInOrderPerUserLimit(int maxLots, User user) {
        super(maxLots);
        this.user = user;
    }

    @Override
    public boolean check(Order order) {
        if (!order.getUser().equals(user)) return true;
        return order.getLotCount() <= maxLots;
    }
}
