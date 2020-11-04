package org.example.limitchecker.model.limit;

import org.example.limitchecker.repository.CheckedOrdersStorage;
import org.junit.jupiter.api.BeforeEach;

public abstract class AbstractLimitTest {

    CheckedOrdersStorage storage;

    @BeforeEach
    void setUp() {
        storage = new CheckedOrdersStorage();
    }
}