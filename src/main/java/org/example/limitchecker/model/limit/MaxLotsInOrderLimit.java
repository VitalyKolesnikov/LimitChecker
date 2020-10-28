package org.example.limitchecker.model.limit;

import org.example.limitchecker.model.Order;

public class MaxLotsInOrderLimit implements Limit {

    private final int maxLots;

    public MaxLotsInOrderLimit(int maxLots) {
        this.maxLots = maxLots;
    }

    @Override
    public boolean check(Order order) {
        return order.getLotCount() <= maxLots;
    }
}