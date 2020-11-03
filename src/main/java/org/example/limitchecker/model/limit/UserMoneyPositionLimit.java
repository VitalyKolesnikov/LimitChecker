package org.example.limitchecker.model.limit;

import org.example.limitchecker.repository.ProcessedOrdersStorage;
import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.User;

public class UserMoneyPositionLimit implements Limit {
    private final int minPosition;
    private final int maxPosition;
    private final User user;

    public UserMoneyPositionLimit(int minPosition, int maxPosition, User user) {
        this.minPosition = minPosition;
        this.maxPosition = maxPosition;
        this.user = user;
    }

    @Override
    public boolean check(Order order, ProcessedOrdersStorage storage) {
        if (!order.getUser().equals(user)) return true;
        double potentialPosition = storage.getUserMoneyPosition(order.getUser()) + storage.computeMoneyPositionChange(order);
        if (potentialPosition < minPosition) return false;
        return potentialPosition <= maxPosition;
    }
}