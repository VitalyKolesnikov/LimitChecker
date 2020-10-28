package org.example.limitchecker.util;

import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.Side;
import org.example.limitchecker.model.User;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OrdersGenerator {

    private static final int NUM = 15;
    private static final Random RANDOM = new Random();
    private static final String ORDERS = "src/main/resources/orders.ser";

    public static List<Order> generate() {
        List<Order> result = new ArrayList<>();
        for (int i = 1; i <= NUM; i++) {
            LocalTime time = LocalTime.now();
            User user = User.getRandom();
            String symbol = StockUtils.getRandomStock();
            int lotCount = RANDOM.nextInt(100);
            Side side = RANDOM.nextInt(10) > 5 ? Side.BUY : Side.SELL;
            Integer price = RANDOM.nextInt(1000);

            Order order = new Order(i, time, user, symbol, lotCount, side, price);
            result.add(order);
        }
        return result;
    }

    public static void writeToFile() {
        List<Order> list = generate();
        try (FileOutputStream fos = new FileOutputStream(ORDERS);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            for (Order order : list) {
                oos.writeObject(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Order> getFromFile() {
        List<Order> result = new ArrayList<>();
        try (FileInputStream file = new FileInputStream(ORDERS);
             ObjectInputStream reader = new ObjectInputStream(file)) {
            while (true) {
                try {
                    Order order = (Order) reader.readObject();
                    result.add(order);
                } catch (Exception ex) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}