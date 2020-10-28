package org.example.limitchecker.model.limit;

import org.example.limitchecker.model.Order;

public interface Limit {
    boolean check(Order order);
}
