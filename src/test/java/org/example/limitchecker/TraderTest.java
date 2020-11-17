package org.example.limitchecker;

import org.example.limitchecker.model.Order;
import org.example.limitchecker.model.OrderTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.mockito.Mockito.*;

class TraderTest {

    LimitChecker checker;
    Trader trader;

    @BeforeEach
    void setUp() {
        checker = mock(LimitChecker.class);
        trader = new Trader(checker, new ArrayList<>());
    }

    @Test
    void newTraderShouldRegisterInChecker() {
        verify(checker).registerTrader(trader);
        verify(checker, never()).deregisterTrader(trader);
    }

    @Test
    void submitOrderTask() throws InterruptedException {
        trader.submitOrderTask(any(Order.class));
        verify(checker).submitOrderTask(any(OrderTask.class));
    }

    @Test
    void traderShouldDeregisterWhenFinished() throws InterruptedException {
        new Thread(trader).start();
        ExecutorService checkerExecutor = Executors.newSingleThreadExecutor();
        checkerExecutor.submit(checker);
        checkerExecutor.shutdown();
        Thread.sleep(10);
        verify(checker).deregisterTrader(trader);
    }
}