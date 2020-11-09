package org.example.limitchecker.repository;

import org.junit.jupiter.api.Test;

import static ch.qos.logback.core.util.AggregationType.NOT_FOUND;
import static org.example.limitchecker.TestData.USER_MIKE;
import static org.example.limitchecker.TestData.USER_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;

class UserStorageTest {

    UserStorage storage = new UserStorage();

    @Test
    void getByName() {
        assertEquals(USER_MIKE, storage.getByName("Mike"));
    }

    @Test
    void getNotFound() {
        assertNull(USER_NOT_FOUND);
    }

    @Test
    void getUserListShouldReturnUnmodifiableList() {
        assertThrows(UnsupportedOperationException.class, () -> storage.getUserList().remove(USER_MIKE));
        assertThrows(UnsupportedOperationException.class, () -> storage.getUserList().add(USER_MIKE));
    }
}