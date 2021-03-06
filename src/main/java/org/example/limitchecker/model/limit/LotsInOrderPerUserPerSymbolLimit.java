package org.example.limitchecker.model.limit;

import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.User;
import org.example.limitchecker.repository.CheckedOrdersStorage;

public class LotsInOrderPerUserPerSymbolLimit extends LotsInOrderPerUserLimit{

    private final String symbol;

    public LotsInOrderPerUserPerSymbolLimit(int maxLots, User user, String symbol) {
        super(maxLots, user);
        this.symbol = symbol;
    }

    @Override
    public boolean check(Order order, CheckedOrdersStorage storage) {
        if (!order.getUser().equals(user) || !order.getStock().getSymbol().equals(symbol)) return true;
        return order.getLotCount() <= maxLots;
    }
}