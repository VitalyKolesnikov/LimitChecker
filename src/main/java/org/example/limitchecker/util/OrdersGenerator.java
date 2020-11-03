package org.example.limitchecker.util;

import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.Side;
import org.example.limitchecker.model.Stock;
import org.example.limitchecker.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class OrdersGenerator {

    private static final Logger log = LoggerFactory.getLogger(OrdersGenerator.class);

    private static final int ORDERS_NUM = 1_000_000;
    private static final Random RANDOM = new Random();
    private static final String ORDERS_PATH = "src/main/resources/orders_1m.ser";

    public static void main(String[] args) {
        writeOrdersToFile();
    }

    public static List<Order> generate() {
        List<Order> result = new ArrayList<>();
        for (int i = 1; i <= ORDERS_NUM; i++) {
            LocalTime time = LocalTime.now();
            User user = User.getRandom();
            Stock stock = StockUtils.getRandomStock();
            int lotCount = RANDOM.nextInt(100);
            Side side = RANDOM.nextInt(10) > 4 ? Side.BUY : Side.SELL;
            Double price = RANDOM.nextInt(10) > 8 ? null : randomPriceChange(stock.getPrice(), side);

            Order order = new Order(i, time, user, stock, lotCount, side, price);
            result.add(order);
        }
        return result;
    }

    public static void writeOrdersToFile() {
        List<Order> list = generate();
        try (FileOutputStream fos = new FileOutputStream(ORDERS_PATH);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            for (Order order : list) {
                oos.writeObject(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Order> getOrdersFromFile(int amount) {
        log.info("Loading orders from file...");
        List<Order> result = new ArrayList<>();
        try (FileInputStream file = new FileInputStream(ORDERS_PATH);
             ObjectInputStream reader = new ObjectInputStream(file)) {
            for (int i = 0; i < amount; i++) {
                try {
                    Order order = (Order) reader.readObject();
                    if (order.getPrice() == null) {
                        order.setPrice(order.getStock().getPrice());
                    }
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

    public static double randomPriceChange(double price, Side side) {
        double random = ThreadLocalRandom.current().nextDouble(0.001, 0.01);
        double newPrice = side.equals(Side.BUY) ? price - price * random : price + price * random;
        BigDecimal bd = new BigDecimal(Double.toString(newPrice)).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}