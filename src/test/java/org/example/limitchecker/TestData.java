package org.example.limitchecker;

import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.Side;
import org.example.limitchecker.model.Stock;
import org.example.limitchecker.repository.UserStorage;

import java.time.LocalTime;
import java.util.List;

public class TestData {
    public static final Stock STOCK1 = new Stock("ETSY", 139.73);
    public static final Stock STOCK2 = new Stock("TSLA", 420.28);
    public static final Stock STOCK3 = new Stock("HOME", 21.85);

    public static UserStorage userStorage = new UserStorage();

    public static final Order ORDER1 = new Order(1, LocalTime.now(), userStorage.getByName("Mike"), STOCK1,35,  Side.BUY, 139.16);
    public static final Order ORDER2 = new Order(2, LocalTime.now(), userStorage.getByName("Mike"), STOCK1,75,  Side.BUY, 139.16);
    public static final Order ORDER3 = new Order(3, LocalTime.now(), userStorage.getByName("Sam"),  STOCK2,38, Side.SELL,422.55);
    public static final Order ORDER4 = new Order(4, LocalTime.now(), userStorage.getByName("John"), STOCK3,60,  Side.SELL,23.51);
    public static final Order ORDER5 = new Order(5, LocalTime.now(), userStorage.getByName("John"), STOCK2,60,  Side.BUY, 419.68);
    public static final Order ORDER6 = new Order(6, LocalTime.now(), userStorage.getByName("Mike"), STOCK2,27, Side.SELL,422.55);
    public static final Order ORDER7 = new Order(7, LocalTime.now(), userStorage.getByName("Sam"),  STOCK2,38, Side.BUY,422.55);
    public static final Order ORDER8 = new Order(8, LocalTime.now(), userStorage.getByName("John"), STOCK3,60,  Side.BUY,23.51);
    public static final Order ORDER9 = new Order(9, LocalTime.now(), userStorage.getByName("John"), STOCK3,40,  Side.SELL,23.51);

    public static final List<Order> ORDER_LIST = List.of(ORDER1, ORDER2, ORDER3);
}