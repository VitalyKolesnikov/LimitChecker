package org.example.limitchecker.model;

public class CheckResult {
    private final Order order;
    private final boolean passed;

    public CheckResult(Order order, boolean passed) {
        this.order = order;
        this.passed = passed;
    }

    public Order getOrder() {
        return order;
    }

    public boolean isPassed() {
        return passed;
    }
}