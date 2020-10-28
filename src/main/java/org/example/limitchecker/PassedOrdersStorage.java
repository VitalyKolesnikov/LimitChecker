package org.example.limitchecker;

import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.User;

import java.util.ArrayList;
import java.util.List;

public class PassedOrdersStorage {
    public static List<Order> orderList = new ArrayList<>();

    public static int getSymbolPosition(String symbol) {
        return orderList.stream()
                .filter(order -> order.getSymbol().equals(symbol))
                .mapToInt(Order::getPositionChange)
                .sum();
    }

    public static int getUserOrdersCount(User user) {
        return (int) orderList.stream()
                .filter(order -> order.getUser().equals(user))
                .count();
    }
}