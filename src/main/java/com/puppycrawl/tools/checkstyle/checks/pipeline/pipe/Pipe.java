package com.puppycrawl.tools.checkstyle.checks.pipeline.pipe;

/**
 * Typed unidirectional channel between two filters. Producer writes; consumer
 * reads. The pipe holds no reference to either filter; the only coordination
 * mechanism in the architecture is the data flowing through pipes.
 *
 * @param <T> message type carried by this pipe
 */
public interface Pipe<T> {

    void write(T message);

    T read();

    boolean hasNext();

    void close();
}
