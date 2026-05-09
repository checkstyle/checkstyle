package com.puppycrawl.tools.checkstyle.checks.pipeline.pipe;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * FIFO buffered pipe. Used for splitter outputs (1 input - many outputs) and
 * the violation sink (one file may yield many violations).
 *
 * @param <T> message type
 */
public final class QueuePipe<T> implements Pipe<T> {

    private final Deque<T> buffer = new ArrayDeque<>();
    private boolean closed;

    @Override
    public void write(T message) {
        buffer.addLast(message);
    }

    @Override
    public T read() {
        return buffer.pollFirst();
    }

    @Override
    public boolean hasNext() {
        return !buffer.isEmpty();
    }

    @Override
    public void close() {
        closed = true;
    }

    public boolean isClosed() {
        return closed;
    }
}
