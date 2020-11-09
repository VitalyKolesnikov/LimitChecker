package org.example.limitchecker.repository;

import org.junit.jupiter.api.Test;

import static ch.qos.logback.core.util.AggregationType.NOT_FOUND;
import static org.example.limitchecker.TestData.USER_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;

class UserStorageTest {

    UserStorage userStorage = new UserStorage();

    @Test
    void getByName() {
        assertNull(USER_NOT_FOUND);
    }
}