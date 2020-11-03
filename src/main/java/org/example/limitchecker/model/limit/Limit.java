package org.example.limitchecker.model.limit;

import org.example.limitchecker.model.Order;
import org.example.limitchecker.repository.ProcessedOrdersStorage;

public interface Limit {
    boolean check(Order order, ProcessedOrdersStorage storage);
}
