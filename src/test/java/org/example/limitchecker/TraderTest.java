package org.example.limitchecker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.example.limitchecker.TestData.ORDER_LIST;
import static org.mockito.Mockito.*;

class TraderTest {

    @Mock
    LimitChecker checker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void newTrader_ShouldBeRegistered_InChecker() {
        new Trader(checker, ORDER_LIST);
        verify(checker, times(1)).registerTrader();
        verify(checker, never()).deregisterTrader();
    }

}