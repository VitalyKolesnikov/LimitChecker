package org.example.limitchecker.model;

import java.io.Serializable;
import java.time.LocalTime;

public class Order implements Serializable {

    private final int id;
    private final LocalTime time;
    private final User user;
    private final Stock stock;
    private final int lotCount;
    private final Side side;
    private final Double price;

    public Order(int id, LocalTime time, User user, Stock stock, int lotCount, Side side, Double price) {
        this.id = id;
        this.time = time;
        this.user = user;
        this.stock = stock;
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

    public Stock getStock() {
        return stock;
    }

    public int getLotCount() {
        return lotCount;
    }

    public Side getSide() {
        return side;
    }

    public Double getPrice() {
        return price;
    }

    public int getPositionChange() {
        return side.equals(Side.BUY) ? lotCount : -(lotCount);
    }

    public double getMoneyPositionChange() {
        double sum = lotCount * price;
        return side.equals(Side.BUY) ? sum : -(sum);
    }

    @Override
    public String toString() {
        return "{" + id +
                ", " + time +
                ", " + user +
                ", " + stock +
                ", " + lotCount +
                ", " + side +
                ", " + price +
                '}';
    }

}