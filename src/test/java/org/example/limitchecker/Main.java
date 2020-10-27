package org.example.limitchecker;

import org.example.limitchecker.util.OrdersGenerator;

public class Main {
    public static void main(String[] args) {
//        List<Order> list = OrdersGenerator.generate();
//        for (Order order : list) {
//            System.out.println(order);
//        }

//        OrdersGenerator.writeToFile();

        OrdersGenerator.readFromFile();
    }
}