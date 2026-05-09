package com.puppycrawl.tools.checkstyle.checks.pipeline.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.pipeline.message.Measurement;
import com.puppycrawl.tools.checkstyle.checks.pipeline.message.ViolationMessage;
import com.puppycrawl.tools.checkstyle.checks.pipeline.pipe.QueuePipe;
import com.puppycrawl.tools.checkstyle.checks.pipeline.pipe.SingletonPipe;

class ThresholdFilterTest {

    @Test
    void emitsViolationWhenValueExceedsMax() {
        final ThresholdFilter filter = new ThresholdFilter(10);
        final SingletonPipe<Measurement> in = new SingletonPipe<>();
        final QueuePipe<ViolationMessage> out = new QueuePipe<>();
        in.write(new Measurement(null, 4, 2, 11, "key", 11, 10));
        filter.process(in, out);
        final ViolationMessage v = out.read();
        assertNotNull(v);
        assertEquals(4, v.getLine());
        assertEquals(2, v.getCol());
        assertEquals("key", v.getMessageKey());
    }

    @Test
    void noViolationWhenValueWithinMax() {
        final ThresholdFilter filter = new ThresholdFilter(10);
        final SingletonPipe<Measurement> in = new SingletonPipe<>();
        final QueuePipe<ViolationMessage> out = new QueuePipe<>();
        in.write(new Measurement(null, 1, 1, 10, "key", 10, 10));
        filter.process(in, out);
        assertFalse(out.hasNext());
    }
}
