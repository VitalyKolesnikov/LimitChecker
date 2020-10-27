package org.example.limitchecker;

import org.example.limitchecker.model.Order;
import org.example.limitchecker.util.OrdersGenerator;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Order> list = OrdersGenerator.GenerateOrders();
        for (Order order : list) {
            System.out.println(order);
        }
    }
}