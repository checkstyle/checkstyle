package com.puppycrawl.tools.checkstyle.checks.pipeline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.puppycrawl.tools.checkstyle.checks.pipeline.pipe.Pipe;
import com.puppycrawl.tools.checkstyle.checks.pipeline.pipe.QueuePipe;
import com.puppycrawl.tools.checkstyle.checks.pipeline.pipe.SingletonPipe;

/**
 * Fluent builder that chains filters in declaration order. Type compatibility
 * between adjacent stages is enforced by the generic parameters.
 *
 * @param <HEAD> head input type (fixed by {@link #start(Pipe)})
 * @param <CURRENT> current tail output type (advances with each {@link #add})
 */
public final class PipelineBuilder<HEAD, CURRENT> {

    private final Pipe<HEAD> head;
    private Pipe<CURRENT> tail;
    private final List<Pipeline.Stage<?, ?>> stages = new ArrayList<>();

    private PipelineBuilder(Pipe<HEAD> head, Pipe<CURRENT> tail) {
        this.head = head;
        this.tail = tail;
    }

    public static <T> PipelineBuilder<T, T> start() {
        final Pipe<T> p = new SingletonPipe<>();
        return new PipelineBuilder<>(p, p);
    }

    public static <T> PipelineBuilder<T, T> start(Pipe<T> headPipe) {
        return new PipelineBuilder<>(headPipe, headPipe);
    }

    public <NEXT> PipelineBuilder<HEAD, NEXT> add(Filter<CURRENT, NEXT> filter) {
        return add(filter, new SingletonPipe<>());
    }

    public <NEXT> PipelineBuilder<HEAD, NEXT> addQueued(Filter<CURRENT, NEXT> filter) {
        return add(filter, new QueuePipe<>());
    }

    private <NEXT> PipelineBuilder<HEAD, NEXT> add(Filter<CURRENT, NEXT> filter,
                                                    Pipe<NEXT> nextPipe) {
        if (filter == null) {
            throw new IllegalArgumentException("filter must not be null");
        }
        stages.add(new Pipeline.Stage<>(filter, tail, nextPipe));
        @SuppressWarnings({"unchecked", "rawtypes"})
        final PipelineBuilder<HEAD, NEXT> next = (PipelineBuilder) this;
        next.tail = (Pipe) nextPipe;
        return next;
    }

    public Pipeline<HEAD, CURRENT> build() {
        return new Pipeline<>(Collections.unmodifiableList(new ArrayList<>(stages)), head, tail);
    }
}
