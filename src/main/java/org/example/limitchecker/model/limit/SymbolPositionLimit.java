package org.example.limitchecker.model.limit;

import org.example.limitchecker.PassedOrdersStorage;
import org.example.limitchecker.model.Order;

public class SymbolPositionLimit implements Limit {

    protected final int minPosition;
    protected final int maxPosition;

    public SymbolPositionLimit(int minPosition, int maxPosition) {
        this.minPosition = minPosition;
        this.maxPosition = maxPosition;
    }

    @Override
    public boolean check(Order order) {
        int potentialPosition = PassedOrdersStorage.getSymbolPosition(order.getSymbol()) + order.getPositionChange();
        if (potentialPosition < minPosition) return false;
        return potentialPosition <= maxPosition;
    }
}