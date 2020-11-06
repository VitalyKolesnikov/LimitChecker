package org.example.limitchecker.util;

import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.Side;
import org.example.limitchecker.model.Stock;
import org.example.limitchecker.model.User;
import org.example.limitchecker.repository.UserStorage;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.example.limitchecker.repository.Database.ORDERS_PATH;
import static org.example.limitchecker.repository.Database.STOCKS_PATH;

public class OrderGenerator {

    public static final int ORDERS_NUM = 1_000_000;

    public static void main(String[] args) {
        OrderGenerator generator = new OrderGenerator();
        List<Order> orderList = generator.generate(ORDERS_NUM);
        generator.writeToFile(orderList);
    }

    public List<Order> generate(int amount) {
        List<Order> result = new ArrayList<>();
        StockLoader stockLoader = new StockLoader(STOCKS_PATH);
        UserStorage userStorage = new UserStorage();
        Random random = new Random();
        for (int i = 1; i <= amount; i++) {
            LocalTime time = LocalTime.now();
            User user = userStorage.getRandom();
            Stock stock = stockLoader.getRandomStock();
            int lotCount = random.nextInt(100);
            Side side = random.nextInt(10) > 4 ? Side.BUY : Side.SELL;
            Double price = random.nextInt(10) > 8 ? null : randomPriceChange(stock.getPrice(), side);

            Order order = new Order(i, time, user, stock, lotCount, side, price);
            result.add(order);
        }
        return result;
    }

    public void writeToFile(List<? extends Serializable> list) {
        try (FileOutputStream fos = new FileOutputStream(ORDERS_PATH);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            for (Serializable element : list) {
                oos.writeObject(element);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double randomPriceChange(double price, Side side) {
        double random = ThreadLocalRandom.current().nextDouble(0.001, 0.01);
        double newPrice = side.equals(Side.BUY) ? price - price * random : price + price * random;
        BigDecimal bd = new BigDecimal(Double.toString(newPrice)).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}