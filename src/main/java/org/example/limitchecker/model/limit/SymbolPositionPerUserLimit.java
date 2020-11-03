package org.example.limitchecker.model.limit;

import org.example.limitchecker.repository.ProcessedOrdersStorage;
import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.User;

public class SymbolPositionPerUserLimit extends SymbolPositionLimit {

    private final User user;

    public SymbolPositionPerUserLimit(int minPosition, int maxPosition, String symbol, User user) {
        super(minPosition, maxPosition, symbol);
        this.user = user;
    }

    @Override
    public boolean check(Order order, ProcessedOrdersStorage storage) {
        if (!order.getStock().getSymbol().equals(symbol) || !order.getUser().equals(user)) return true;
        int potentialPosition = storage.getSymbolPositionPerUser(order.getStock().getSymbol(), order.getUser()) + storage.computePositionChange(order);
        if (potentialPosition < minPosition) return false;
        return potentialPosition <= maxPosition;
    }
}