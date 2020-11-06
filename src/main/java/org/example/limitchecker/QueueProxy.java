package org.example.limitchecker;

import org.example.limitchecker.model.Order;

public interface QueueProxy {
    void put(Order element) throws InterruptedException;
}