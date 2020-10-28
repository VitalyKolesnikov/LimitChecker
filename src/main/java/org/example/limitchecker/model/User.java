package org.example.limitchecker.model;

import java.util.Random;

public enum User {
    JOHN,
    MIKE,
    SAM,
    CHARLIE,
    ROBERT,
//    STEVEN,
//    COREY,
//    BARBARA,
//    MICHELE,
    SARAH;

    public static User getRandom() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}