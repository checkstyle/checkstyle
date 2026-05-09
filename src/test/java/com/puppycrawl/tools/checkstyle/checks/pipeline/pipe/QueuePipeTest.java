package com.puppycrawl.tools.checkstyle.checks.pipeline.pipe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class QueuePipeTest {

    @Test
    void preservesFifoOrder() {
        final QueuePipe<Integer> pipe = new QueuePipe<>();
        pipe.write(1);
        pipe.write(2);
        pipe.write(3);
        assertEquals(1, pipe.read());
        assertEquals(2, pipe.read());
        assertEquals(3, pipe.read());
        assertFalse(pipe.hasNext());
        assertNull(pipe.read());
    }

    @Test
    void closeIsIdempotent() {
        final QueuePipe<Integer> pipe = new QueuePipe<>();
        pipe.close();
        pipe.close();
        assertTrue(pipe.isClosed());
    }
}
