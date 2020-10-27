package org.example.limitchecker.model;

import java.io.Serializable;
import java.time.LocalTime;

public class Order implements Serializable {

    private final int id;
    private final LocalTime time;
    private final User user;
    private final String symbol;
    private final int shareCount;
    private final Side side;
    private final Integer price;

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