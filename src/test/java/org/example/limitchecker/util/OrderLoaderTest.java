package org.example.limitchecker.util;

import org.example.limitchecker.model.Order;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.example.limitchecker.repository.Database.ORDERS_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderLoaderTest {

    OrderLoader orderLoader = new OrderLoader(ORDERS_PATH);

    @Test
    void loadOrdersFromFile() {
        List<Order> orderList = orderLoader.loadOrdersFromFile(1000);
        assertEquals(1000, orderList.size());
    }
}