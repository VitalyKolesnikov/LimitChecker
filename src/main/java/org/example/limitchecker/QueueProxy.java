package org.example.limitchecker;

import org.example.limitchecker.model.CheckResult;
import org.example.limitchecker.model.OrderTask;

public interface QueueProxy {
    void submitOrderTask(OrderTask orderTask) throws InterruptedException;
    void registerTrader(Trader trader);
    void deregisterTrader(Trader trader);
    CheckResult getResult(Trader trader);
}