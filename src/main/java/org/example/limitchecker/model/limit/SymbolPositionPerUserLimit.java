package org.example.limitchecker.model.limit;

import org.example.limitchecker.ProcessedOrdersStorage;
import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.User;

public class SymbolPositionPerUserLimit extends SymbolPositionLimit {

    private final User user;

    public SymbolPositionPerUserLimit(int minPosition, int maxPosition, String symbol, User user) {
        super(minPosition, maxPosition, symbol);
        this.user = user;
    }

    @Override
    public boolean check(Order order) {
        if (!order.getStock().getSymbol().equals(symbol) || !order.getUser().equals(user)) return true;
        int potentialPosition = ProcessedOrdersStorage.getSymbolPositionPerUser(order.getStock().getSymbol(), order.getUser()) + order.getPositionChange();
        if (potentialPosition < minPosition) return false;
        return potentialPosition <= maxPosition;
    }
}