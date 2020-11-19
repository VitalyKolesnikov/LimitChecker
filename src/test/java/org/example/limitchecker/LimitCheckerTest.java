package org.example.limitchecker;

import org.example.limitchecker.repository.CheckedOrdersStorage;
import org.example.limitchecker.repository.Database;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.example.limitchecker.TestData.ORDER1;
import static org.example.limitchecker.TestData.ORDER_LIST;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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
    void takeFromEmptyQueue() throws InterruptedException {
        CheckedOrdersStorage storage = new CheckedOrdersStorage();
        LimitChecker checker = new LimitChecker(new Database().getLimits(), storage);
        new Trader(checker, ORDER_LIST);
        ExecutorService checkerExecutor = Executors.newSingleThreadExecutor();
        Future<Integer> checkerResult = checkerExecutor.submit(checker);
        checkerExecutor.shutdown();

        checker.checkOrder();
        Thread.sleep(50);

        assertFalse(checkerResult.isDone());
    }
}