package org.example.limitchecker.model.limit;

import org.example.limitchecker.PassedOrdersStorage;
import org.example.limitchecker.model.Order;

public class UserMoneyPositionLimit implements Limit {
    private final int minPosition;
    private final int maxPosition;

    public UserMoneyPositionLimit(int minPosition, int maxPosition) {
        this.minPosition = minPosition;
        this.maxPosition = maxPosition;
    }

    @Override
    public boolean check(Order order) {
        double potentialPosition = PassedOrdersStorage.getUserMoneyPosition(order.getUser()) + order.getMoneyPositionChange();
        if (potentialPosition < minPosition) return false;
        return potentialPosition <= maxPosition;
    }
}