package com.puppycrawl.tools.checkstyle.checks.pipeline.pipe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SingletonPipeTest {

    @Test
    void writeThenReadReturnsMessage() {
        final SingletonPipe<String> pipe = new SingletonPipe<>();
        pipe.write("hello");
        assertTrue(pipe.hasNext());
        assertEquals("hello", pipe.read());
        assertFalse(pipe.hasNext());
        assertNull(pipe.read());
    }

    @Test
    void secondWriteOverwritesUnreadMessage() {
        final SingletonPipe<String> pipe = new SingletonPipe<>();
        pipe.write("first");
        pipe.write("second");
        assertEquals("second", pipe.read());
        assertNull(pipe.read());
    }

    @Test
    void closeMarksClosed() {
        final SingletonPipe<String> pipe = new SingletonPipe<>();
        assertFalse(pipe.isClosed());
        pipe.close();
        assertTrue(pipe.isClosed());
    }
}
