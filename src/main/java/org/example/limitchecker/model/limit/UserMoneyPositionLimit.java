package org.example.limitchecker.model.limit;

import org.example.limitchecker.repository.CheckedOrdersStorage;
import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.User;

public class UserMoneyPositionLimit implements Limit {
    private final int minPosition;
    private final int maxPosition;
    private final User user;

    public UserMoneyPositionLimit(int minPosition, int maxPosition, User user) {
        if (minPosition > maxPosition) throw new IllegalArgumentException();
        this.minPosition = minPosition;
        this.maxPosition = maxPosition;
        this.user = user;
    }

    @Override
    public boolean check(Order order, CheckedOrdersStorage storage) {
        if (!order.getUser().equals(user)) return true;
        Double currentPosition = storage.getUserMoneyPosition(order.getUser());
        double positionChange = storage.computeMoneyPositionChange(order);
        double potentialPosition = currentPosition == null ? positionChange : currentPosition + positionChange;
        if (potentialPosition < minPosition) return false;
        return potentialPosition <= maxPosition;
    }
}