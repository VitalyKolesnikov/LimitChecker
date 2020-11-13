package org.example.limitchecker;

import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.OrderTask;
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

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TraderTest {

    @Mock
    LimitChecker checker;

    @Mock
    List<Order> orderList;

    @Mock
    Iterator<Order> orderIterator;

    @InjectMocks
    Trader trader;

    @Test
    void newTrader_ShouldBeRegistered_InChecker() {
        verify(checker).registerTrader();
        verify(checker, never()).deregisterTrader();
    }

    @Test
    void submitOrderTask() throws InterruptedException {
        trader.submitOrderTask(any(Order.class));
        verify(checker).submitOrderTask(any(OrderTask.class));
    }

    @Test
    void trader_ShouldDeregister_WhenFinished() throws InterruptedException {
        when(orderList.iterator()).thenReturn(orderIterator);
        new Thread(trader).start();
        ExecutorService checkerExecutor = Executors.newSingleThreadExecutor();
        checkerExecutor.submit(checker);
        checkerExecutor.shutdown();
        Thread.sleep(10);
        verify(checker).deregisterTrader();
    }
}