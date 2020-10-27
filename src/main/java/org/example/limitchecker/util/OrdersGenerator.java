package org.example.limitchecker.util;

import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.Side;
import org.example.limitchecker.model.User;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OrdersGenerator {

    private static final int NUM = 10;
    private static final Random RANDOM = new Random();

    public static List<Order> GenerateOrders() {
        List<Order> result = new ArrayList<>();
        for (int i = 1; i <= NUM; i++) {
            LocalTime time = LocalTime.now();
            User user = User.getRandom();
            String sy = StockUtils.getRandomStock();
            int sh = RANDOM.nextInt(100);
            Side side = RANDOM.nextInt(10) > 5 ? Side.BUY : Side.SELL;
            Integer price = RANDOM.nextInt(1000);

            Order order = new Order(i, time, user, sy, sh, side, price);
            result.add(order);
        }
        return result;
    }
}