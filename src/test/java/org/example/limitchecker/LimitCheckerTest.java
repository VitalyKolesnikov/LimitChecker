package org.example.limitchecker;

import org.example.limitchecker.repository.CheckedOrdersStorage;
import org.example.limitchecker.repository.Database;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.example.limitchecker.TestData.ORDER1;
import static org.example.limitchecker.TestData.ORDER_LIST;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class LimitCheckerTest {

    @Test
    void passedOrdersShouldBeAddedToStorage() throws InterruptedException {
        CheckedOrdersStorage storage = mock(CheckedOrdersStorage.class);
        LimitChecker checker = new LimitChecker(new Database().getLimits(), storage);

        new Thread(new Trader(checker, List.of(ORDER1))).start();
        checker.checkOrder();

        verify(storage).addOrder(ORDER1);
    }

    @Test
    void checkOrder() throws InterruptedException {
        CheckedOrdersStorage storage = mock(CheckedOrdersStorage.class);
        LimitChecker checker = new LimitChecker(new Database().getLimits(), storage);

        new Thread(new Trader(checker, ORDER_LIST)).start();

        assertTrue(checker.checkOrder());
        assertFalse(checker.checkOrder());
        assertFalse(checker.checkOrder());
    }

}