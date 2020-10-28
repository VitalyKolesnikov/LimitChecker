package org.example.limitchecker.model;

import java.io.Serializable;
import java.time.LocalTime;

public class Order implements Serializable {

    private final int id;
    private final LocalTime time;
    private final User user;
    private final String symbol;
    private final int lotCount;
    private final Side side;
    private final Integer price;

    public Order(int id, LocalTime time, User user, String symbol, int lotCount, Side side, Integer price) {
        this.id = id;
        this.time = time;
        this.user = user;
        this.symbol = symbol;
        this.lotCount = lotCount;
        this.side = side;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }

    public User getUser() {
        return user;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getLotCount() {
        return lotCount;
    }

    public Side getSide() {
        return side;
    }

    public Integer getPrice() {
        return price;
    }

    public int getPositionChange() {
        return side.equals(Side.BUY) ? lotCount : -(lotCount);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", time=" + time +
                ", user=" + user +
                ", symbol='" + symbol + '\'' +
                ", lotCount=" + lotCount +
                ", side=" + side +
                ", price=" + price +
                '}';
    }

    public String getShortInfo() {
        return "[" + user +
                ", " + symbol +
                ", " + lotCount +
                ", " + side +
                ", " + price +
                ']';
    }
}