package org.example.limitchecker;

import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.Side;
import org.example.limitchecker.model.Stock;
import org.example.limitchecker.model.User;

import java.time.LocalTime;

public class TestData {
    public static Stock STOCK1 = new Stock("ETSY", 139.73);

    public static Order ORDER1 = new Order(1, LocalTime.now(), User.MIKE, STOCK1,35, Side.BUY,139.16);
    public static Order ORDER2 = new Order(2, LocalTime.now(), User.MIKE, STOCK1,75, Side.BUY,139.16);
    public static Order ORDER3 = new Order(3, LocalTime.now(), User.MIKE, STOCK1,60, Side.SELL,139.22);
}