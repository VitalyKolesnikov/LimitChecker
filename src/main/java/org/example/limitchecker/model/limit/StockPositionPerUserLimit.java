package org.example.limitchecker.model.limit;

import org.example.limitchecker.PassedOrdersStorage;
import org.example.limitchecker.model.Order;

public class StockPositionPerUserLimit extends StockPositionLimit {

    public StockPositionPerUserLimit(int minPosition, int maxPosition) {
        super(minPosition, maxPosition);
    }

    @Override
    public boolean check(Order order) {
        int potentialPosition = PassedOrdersStorage.getStockPositionPerUser(order.getStock(), order.getUser()) + order.getPositionChange();
        if (potentialPosition < minPosition) return false;
        return potentialPosition <= maxPosition;
    }
}