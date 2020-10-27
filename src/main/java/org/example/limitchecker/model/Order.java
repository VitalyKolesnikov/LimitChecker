package org.example.limitchecker.model;

import java.time.LocalTime;

public class Order {
    private int id;
    private LocalTime time;
    private User user;
    private String sy;
    private int sh;
    private Side side;
    private Integer price;
}