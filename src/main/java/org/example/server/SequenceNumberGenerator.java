package org.example.server;

import java.util.concurrent.atomic.AtomicLong;

public class SequenceNumberGenerator {

    private final AtomicLong sequenceNumber = new AtomicLong(0);

    // Returns the next sequential long
    public long next() {
        return sequenceNumber.getAndIncrement();
    }

    // Optionally: peek the current value without incrementing
    public long current() {
        return sequenceNumber.get();
    }
}
