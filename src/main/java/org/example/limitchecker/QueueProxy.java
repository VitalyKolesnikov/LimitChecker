package org.example.limitchecker;

import org.example.limitchecker.model.OrderTask;

public interface QueueProxy {
    void submitOrderTask(OrderTask orderTask) throws InterruptedException;
    void registerTrader();
    void deregisterTrader();
}