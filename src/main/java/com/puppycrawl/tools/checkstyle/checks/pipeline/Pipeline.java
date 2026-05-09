package com.puppycrawl.tools.checkstyle.checks.pipeline;

import java.util.List;

import com.puppycrawl.tools.checkstyle.checks.pipeline.pipe.Pipe;
import com.puppycrawl.tools.checkstyle.checks.pipeline.pipe.QueuePipe;

/**
 * Composed, immutable chain of filter stages. Driver feeds the head pipe via
 * {@link #submit(Object)} and drains the tail pipe via {@link #drain()}.
 *
 * @param <HEAD> head input type
 * @param <TAIL> tail output type
 */
public final class Pipeline<HEAD, TAIL> {

    private final List<Stage<?, ?>> stages;
    private final Pipe<HEAD> head;
    private final Pipe<TAIL> tail;

    Pipeline(List<Stage<?, ?>> stages, Pipe<HEAD> head, Pipe<TAIL> tail) {
        this.stages = stages;
        this.head = head;
        this.tail = tail;
    }

    public void submit(HEAD msg) {
        head.write(msg);
        runStages();
    }

    public TAIL drain() {
        return tail.read();
    }

    public boolean hasResults() {
        return tail.hasNext();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void runStages() {
        for (Stage stage : stages) {
            stage.filter.process(stage.in, stage.out);
        }
    }

    /** Internal record-like binding of a filter to its two pipes. */
    static final class Stage<I, O> {
        final Filter<I, O> filter;
        final Pipe<I> in;
        final Pipe<O> out;

        Stage(Filter<I, O> filter, Pipe<I> in, Pipe<O> out) {
            this.filter = filter;
            this.in = in;
            this.out = out;
        }
    }

    /** Default tail buffer used by the builder. */
    static <T> Pipe<T> defaultTail() {
        return new QueuePipe<>();
    }
}
