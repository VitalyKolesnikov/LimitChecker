package org.example.limitchecker;

import org.example.limitchecker.model.limit.Limit;
import org.example.limitchecker.repository.CheckedOrdersStorage;
import org.example.limitchecker.repository.Database;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.example.limitchecker.TestData.ORDER1;
import static org.example.limitchecker.TestData.ORDER_LIST;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LimitCheckerTest {

    @Mock
    CheckedOrdersStorage storage;

    @Mock
    List<Limit> limitList;

    @Mock
    Iterator<Limit> limitIterator;

    @InjectMocks
    LimitChecker checker;

    @Test
    void passedOrders_ShouldBeAdded_ToStorage() throws InterruptedException {
        when(limitList.iterator()).thenReturn(limitIterator);
        new Thread(new Trader(checker, List.of(ORDER1))).start();
        checker.checkOrder();
        verify(storage).addOrder(ORDER1);
    }

    @Test
    void takeFromEmptyQueue() throws InterruptedException {
        checker = new LimitChecker(new Database().getLimits(), storage);
        new Trader(checker, ORDER_LIST);
        ExecutorService checkerExecutor = Executors.newSingleThreadExecutor();
        Future<Integer> checkerResult = checkerExecutor.submit(checker);
        checkerExecutor.shutdown();
        checker.checkOrder();
        Thread.sleep(50);
        assertFalse(checkerResult.isDone());
    }
}