package org.example.limitchecker;

import java.util.concurrent.BlockingQueue;

public class QueueProxy<T> {
    private final BlockingQueue<T> queue;


    public QueueProxy(BlockingQueue<T> queue) {
        this.queue = queue;
    }

    public void put(T element) throws InterruptedException {
        queue.put(element);
    }
}