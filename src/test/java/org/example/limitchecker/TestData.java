package org.example.limitchecker;

import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.Side;
import org.example.limitchecker.model.Stock;
import org.example.limitchecker.model.User;
import org.example.limitchecker.repository.UserStorage;

import java.time.LocalTime;
import java.util.List;

public class TestData {
    public static final Stock STOCK1 = new Stock("ETSY", 139.73);
    public static final Stock STOCK2 = new Stock("TSLA", 420.28);
    public static final Stock STOCK3 = new Stock("HOME", 21.85);

    public static final UserStorage userStorage = new UserStorage();

    public static final User USER_MIKE = userStorage.getByName("Mike");
    public static final User USER_JOHN = userStorage.getByName("John");
    public static final User USER_SAM = userStorage.getByName("Sam");
    public static final User USER_NOT_FOUND = userStorage.getByName("Andrew");

    public static final Order ORDER1 = new Order(1, LocalTime.now(), USER_MIKE, STOCK1,35,  Side.BUY, 139.16);
    public static final Order ORDER2 = new Order(2, LocalTime.now(), USER_MIKE, STOCK1,75,  Side.BUY, 139.16);
    public static final Order ORDER3 = new Order(3, LocalTime.now(), USER_SAM,  STOCK2,38, Side.SELL,422.55);
    public static final Order ORDER4 = new Order(4, LocalTime.now(), USER_JOHN, STOCK3,60,  Side.SELL,23.51);
    public static final Order ORDER5 = new Order(5, LocalTime.now(), USER_JOHN, STOCK2,60,  Side.BUY, 419.68);
    public static final Order ORDER6 = new Order(6, LocalTime.now(), USER_MIKE, STOCK2,27, Side.SELL,422.55);
    public static final Order ORDER7 = new Order(7, LocalTime.now(), USER_SAM,  STOCK2,38, Side.BUY,422.55);
    public static final Order ORDER8 = new Order(8, LocalTime.now(), USER_JOHN, STOCK3,60,  Side.BUY,23.51);
    public static final Order ORDER9 = new Order(9, LocalTime.now(), USER_JOHN, STOCK3,40,  Side.SELL,23.51);

    public static final List<Order> ORDER_LIST = List.of(ORDER1, ORDER2, ORDER3);
}