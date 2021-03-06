package org.example.limitchecker.model.limit;

import org.example.limitchecker.repository.CheckedOrdersStorage;
import org.example.limitchecker.model.Order;

public class SymbolPositionLimit implements Limit {

    protected final int minPosition;
    protected final int maxPosition;
    protected final String symbol;

    public SymbolPositionLimit(int minPosition, int maxPosition, String symbol) {
        if (minPosition > maxPosition) throw new IllegalArgumentException();
        this.minPosition = minPosition;
        this.maxPosition = maxPosition;
        this.symbol = symbol;
    }

    @Override
    public boolean check(Order order, CheckedOrdersStorage storage) {
        if (!order.getStock().getSymbol().equals(symbol)) return true;
        Integer currentPosition = storage.getSymbolPosition(order.getStock().getSymbol());
        int positionChange = storage.computePositionChange(order);
        int potentialPosition = currentPosition == null ? positionChange : currentPosition + positionChange;
        if (potentialPosition < minPosition) return false;
        return potentialPosition <= maxPosition;
    }
}