package org.example.limitchecker.model.limit;

import org.example.limitchecker.model.Order;
import org.example.limitchecker.repository.CheckedOrdersStorage;

public interface Limit {
    boolean check(Order order, CheckedOrdersStorage storage);
}
