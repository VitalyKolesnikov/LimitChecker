package org.example.limitchecker.model.limit;

import org.example.limitchecker.model.Order;

public class LotsInOrderLimit implements Limit {

    protected final int maxLots;

    public LotsInOrderLimit(int maxLots) {
        this.maxLots = maxLots;
    }

    @Override
    public boolean check(Order order) {
        return order.getLotCount() <= maxLots;
    }
}