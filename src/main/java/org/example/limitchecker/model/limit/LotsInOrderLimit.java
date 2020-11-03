package org.example.limitchecker.model.limit;

import org.example.limitchecker.model.Order;
import org.example.limitchecker.repository.ProcessedOrdersStorage;

public class LotsInOrderLimit implements Limit {

    protected final int maxLots;

    public LotsInOrderLimit(int maxLots) {
        this.maxLots = maxLots;
    }

    @Override
    public boolean check(Order order, ProcessedOrdersStorage storage) {
        return order.getLotCount() <= maxLots;
    }
}