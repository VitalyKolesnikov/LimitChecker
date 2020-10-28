package org.example.limitchecker.model.limit;

import org.example.limitchecker.PassedOrdersStorage;
import org.example.limitchecker.model.Order;

public class SymbolPositionPerUserLimit extends SymbolPositionLimit {

    public SymbolPositionPerUserLimit(int minPosition, int maxPosition) {
        super(minPosition, maxPosition);
    }

    @Override
    public boolean check(Order order) {
        int potentialPosition = PassedOrdersStorage.getSymbolPositionPerUser(order.getSymbol(), order.getUser()) + order.getPositionChange();
        if (potentialPosition < minPosition) return false;
        return potentialPosition <= maxPosition;
    }
}
