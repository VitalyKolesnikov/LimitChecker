package org.example.limitchecker.model.limit;

import org.example.limitchecker.repository.CheckedOrdersStorage;
import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.User;

public class SymbolPositionPerUserLimit extends SymbolPositionLimit {

    private final User user;

    public SymbolPositionPerUserLimit(int minPosition, int maxPosition, String symbol, User user) {
        super(minPosition, maxPosition, symbol);
        this.user = user;
    }

    @Override
    public boolean check(Order order, CheckedOrdersStorage storage) {
        if (!order.getStock().getSymbol().equals(symbol) || !order.getUser().equals(user)) return true;
        Integer currentPosition = storage.getSymbolPositionPerUser(order.getStock().getSymbol(), order.getUser());
        int positionChange = storage.computePositionChange(order);
        int potentialPosition = currentPosition == null ? positionChange : currentPosition + positionChange;
        if (potentialPosition < minPosition) return false;
        return potentialPosition <= maxPosition;
    }
}