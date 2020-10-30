package org.example.limitchecker.model.limit;

import org.example.limitchecker.ProcessedOrdersStorage;
import org.example.limitchecker.model.Order;

public class SymbolPositionLimit implements Limit {

    protected final int minPosition;
    protected final int maxPosition;
    protected final String symbol;

    public SymbolPositionLimit(int minPosition, int maxPosition, String symbol) {
        this.minPosition = minPosition;
        this.maxPosition = maxPosition;
        this.symbol = symbol;
    }

    @Override
    public boolean check(Order order) {
        if (!order.getStock().getSymbol().equals(symbol)) return true;
        int potentialPosition = ProcessedOrdersStorage.getSymbolPosition(order.getStock().getSymbol()) + order.getPositionChange();
        if (potentialPosition < minPosition) return false;
        return potentialPosition <= maxPosition;
    }
}