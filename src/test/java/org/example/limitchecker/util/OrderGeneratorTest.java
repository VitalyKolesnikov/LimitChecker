package org.example.limitchecker.util;

import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.Side;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderGeneratorTest {

    OrderGenerator generator = new OrderGenerator();

    @Test
    void generate() {
        List<Order> orderList = generator.generate(1000);
        assertThat(orderList).hasSize(1000);
        assertThat(orderList).extracting(Order::getSide).contains(Side.BUY);
        assertThat(orderList).extracting(Order::getSide).contains(Side.SELL);
        assertTrue(orderList.stream().map(Order::getPrice).anyMatch(Objects::isNull));
    }

    @Test
    void randomPriceChange() {
        double price = 0.537;
        double newPrice = generator.randomPriceChange(price, Side.BUY);
        assertNotEquals(price, newPrice);
        assertTrue(newPrice < 0.548);
    }
}