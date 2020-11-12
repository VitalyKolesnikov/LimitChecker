package org.example.limitchecker;

import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.OrderTask;
import org.example.limitchecker.model.limit.Limit;
import org.example.limitchecker.repository.CheckedOrdersStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.example.limitchecker.TestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void takeFromEmptyQueue() throws InterruptedException {
        new Trader(checker, ORDER_LIST);
        Thread checkerThread = new Thread(checker);
        checkerThread.start();
        Thread.sleep(5);
        assertEquals(Thread.State.TIMED_WAITING, checkerThread.getState());
    }

    @Test
    void passedOrders_ShouldBeAdded_ToStorage() throws InterruptedException {
        when(limitList.iterator()).thenReturn(limitIterator);
        checker.submitOrderTask(new OrderTask(new Trader(checker, new ArrayList<>()), ORDER1));
        checker.checkOrder();
        verify(storage).addOrder(ORDER1);
    }

    @Test
    void registerAndDeregisterTrader() throws InterruptedException {
        assertEquals(0, checker.getActiveTradersCount());
        Trader trader1 = new Trader(checker, new ArrayList<>());
        assertEquals(1, checker.getActiveTradersCount());
        Thread thread1 = new Thread(trader1);
        thread1.start();
        thread1.join();
        assertEquals(0, checker.getActiveTradersCount());
    }
}