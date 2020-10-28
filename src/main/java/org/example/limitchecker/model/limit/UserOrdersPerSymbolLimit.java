package org.example.limitchecker.model.limit;

import org.example.limitchecker.PassedOrdersStorage;
import org.example.limitchecker.model.Order;

public class UserOrdersPerSymbolLimit extends UserOrdersLimit {

    public UserOrdersPerSymbolLimit(int maxOrders) {
        super(maxOrders);
    }

    @Override
    public boolean check(Order order) {
        return PassedOrdersStorage.getUserOrdersPerSymbolCount(order.getUser(), order.getSymbol()) < maxOrders;
    }
}