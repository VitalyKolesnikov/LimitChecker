package org.example.limitchecker.model.limit;

import org.example.limitchecker.repository.CheckedOrdersStorage;
import org.example.limitchecker.repository.UserStorage;
import org.junit.jupiter.api.BeforeEach;

public abstract class AbstractLimitTest {

    CheckedOrdersStorage checkedOrdersStorage;
    UserStorage userStorage = new UserStorage();

    @BeforeEach
    void setUp() {
        checkedOrdersStorage = new CheckedOrdersStorage();
    }
}