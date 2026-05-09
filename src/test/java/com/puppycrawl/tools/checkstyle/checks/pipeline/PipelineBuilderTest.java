package com.puppycrawl.tools.checkstyle.checks.pipeline;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.pipeline.pipe.Pipe;

class PipelineBuilderTest {

    @Test
    void chainsTwoFilters() {
        final Filter<Integer, Integer> doubler = new Filter<Integer, Integer>() {
            @Override
            public void process(Pipe<Integer> in, Pipe<Integer> out) {
                while (in.hasNext()) {
                    final Integer v = in.read();
                    if (v == null) break;
                    out.write(v * 2);
                }
            }
        };
        final Filter<Integer, String> stringify = new Filter<Integer, String>() {
            @Override
            public void process(Pipe<Integer> in, Pipe<String> out) {
                while (in.hasNext()) {
                    final Integer v = in.read();
                    if (v == null) break;
                    out.write(String.valueOf(v));
                }
            }
        };
        final Pipeline<Integer, String> pipeline = PipelineBuilder.<Integer>start()
                .add(doubler)
                .add(stringify)
                .build();
        pipeline.submit(7);
        assertEquals("14", pipeline.drain());
        assertFalse(pipeline.hasResults());
        assertNull(pipeline.drain());
    }
}
