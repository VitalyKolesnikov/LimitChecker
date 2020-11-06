package org.example.limitchecker;

import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.Side;
import org.example.limitchecker.model.Stock;
import org.example.limitchecker.repository.UserStorage;

import java.time.LocalTime;
import java.util.List;

public class TestData {
    public static final Stock STOCK1 = new Stock("ETSY", 139.73);

    public static UserStorage userStorage = new UserStorage();

    public static final Order ORDER1 = new Order(1, LocalTime.now(), userStorage.getByName("Mike"), STOCK1,35, Side.BUY,139.16);
    public static final Order ORDER2 = new Order(2, LocalTime.now(), userStorage.getByName("Mike"), STOCK1,75, Side.BUY,139.16);
    public static final Order ORDER3 = new Order(3, LocalTime.now(), userStorage.getByName("Mike"), STOCK1,60, Side.SELL,139.22);

    public static final List<Order> ORDER_LIST = List.of(ORDER1, ORDER2, ORDER3);
}