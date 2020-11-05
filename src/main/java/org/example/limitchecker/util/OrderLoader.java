package org.example.limitchecker.util;

import org.example.limitchecker.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class OrderLoader {

    private static final Logger log = LoggerFactory.getLogger(OrderLoader.class);

    public List<Order> loadOrdersFromFile(String path, int amount) {
        log.info("Loading orders from file...");
        List<Order> result = new ArrayList<>();
        try (FileInputStream file = new FileInputStream(path);
             ObjectInputStream reader = new ObjectInputStream(file)) {
            for (int i = 0; i < amount; i++) {
                try {
                    Order order = (Order) reader.readObject();
                    result.add(order);
                } catch (Exception ex) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}