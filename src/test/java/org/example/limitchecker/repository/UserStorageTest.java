package org.example.limitchecker.repository;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserStorageTest {

    UserStorage userStorage = new UserStorage();

    @Test
    void getByName() {
        assertNull(userStorage.getByName("Andrew"));
    }
}