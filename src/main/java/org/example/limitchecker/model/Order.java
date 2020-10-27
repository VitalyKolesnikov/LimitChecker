package org.example.limitchecker.model;

import java.time.LocalTime;

public class Order {
    private int id;
    private LocalTime time;
    private User user;
    private String symbol;
    private int shareCount;
    private Side side;
    private Integer price;

    public Order(int id, LocalTime time, User user, String symbol, int shareCount, Side side, Integer price) {
        this.id = id;
        this.time = time;
        this.user = user;
        this.symbol = symbol;
        this.shareCount = shareCount;
        this.side = side;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", time=" + time +
                ", user=" + user +
                ", symbol='" + symbol + '\'' +
                ", shareCount=" + shareCount +
                ", side=" + side +
                ", price=" + price +
                '}';
    }
}