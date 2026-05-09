package com.puppycrawl.tools.checkstyle.checks.pipeline.pipe;

/**
 * Single-message-slot pipe. Used between adjacent stages where each input
 * yields at most one output (the common AST path). {@link #write} overwrites
 * any unread message in the slot.
 *
 * @param <T> message type
 */
public final class SingletonPipe<T> implements Pipe<T> {

    private T slot;
    private boolean hasMessage;
    private boolean closed;

    @Override
    public void write(T message) {
        slot = message;
        hasMessage = true;
    }

    @Override
    public T read() {
        if (!hasMessage) {
            return null;
        }
        final T out = slot;
        slot = null;
        hasMessage = false;
        return out;
    }

    @Override
    public boolean hasNext() {
        return hasMessage;
    }

    @Override
    public void close() {
        closed = true;
    }

    public boolean isClosed() {
        return closed;
    }
}
