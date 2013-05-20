package com.github.mybridge.sharding.support;

import java.util.concurrent.atomic.AtomicInteger;

import com.github.mybridge.sharding.UniquePrimaryKey;

public class MemoryMockKey implements UniquePrimaryKey {

    private AtomicInteger id   = new AtomicInteger(1);
    private Object        lock = new Object();

    @Override
    public long get() {
        synchronized (lock) {
            return id.getAndIncrement();
        }

    }

    public static final void main(String args[]) {
        MemoryMockKey key = new MemoryMockKey();
        for (int i = 0; i < 1000000; i++) {
            System.out.println(key.get());
        }
    }
}
