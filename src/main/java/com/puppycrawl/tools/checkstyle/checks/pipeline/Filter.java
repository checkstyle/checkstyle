package com.puppycrawl.tools.checkstyle.checks.pipeline;

import com.puppycrawl.tools.checkstyle.checks.pipeline.pipe.Pipe;

/**
 * Single-stage processing contract. A filter reads zero or more messages from
 * its input pipe, transforms them, and writes zero or more messages to its
 * output pipe. A filter MUST NOT hold a reference to another filter and MUST
 * NOT call into the Checkstyle execution framework.
 *
 * @param <I> input message type
 * @param <O> output message type
 */
public interface Filter<I, O> {

    void process(Pipe<I> in, Pipe<O> out);
}
