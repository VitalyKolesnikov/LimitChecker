package org.example.limitchecker.model.limit;

import org.example.limitchecker.PassedOrdersStorage;
import org.example.limitchecker.model.Order;

public class StockPositionLimit implements Limit {

    protected final int minPosition;
    protected final int maxPosition;

    public StockPositionLimit(int minPosition, int maxPosition) {
        this.minPosition = minPosition;
        this.maxPosition = maxPosition;
    }

    @Override
    public boolean check(Order order) {
        int potentialPosition = PassedOrdersStorage.getStockPosition(order.getStock()) + order.getPositionChange();
        if (potentialPosition < minPosition) return false;
        return potentialPosition <= maxPosition;
    }
}