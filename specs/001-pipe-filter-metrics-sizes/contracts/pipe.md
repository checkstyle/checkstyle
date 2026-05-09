# Contract: `Pipe<T>`

## Java surface

```java
package com.puppycrawl.tools.checkstyle.checks.pipeline.pipe;

public interface Pipe<T> {
    void write(T message);
    T read();              // returns null if drained
    boolean hasNext();
    void close();
}
```

## Invariants

1. **Unidirectional**: pipe exposes no operation that lets a downstream filter signal an upstream filter.
2. **Typed**: generic parameter `T` is the only message type allowed; mixing types is a compile-time error.
3. **No filter reference**: pipe does not hold a reference to any `Filter` instance. The pipe is created by the `PipelineBuilder` and handed to two filters at runtime.
4. **Order-preserving**: `read()` returns messages in the exact order `write()` was called.
5. **Drain semantics**: after `close()` is called and the buffer is empty, `read()` returns `null` and `hasNext()` returns `false` permanently.
6. **Single producer / single consumer**: the pipeline composer guarantees only one filter writes and only one filter reads from any given pipe instance.

## Implementations

### `SingletonPipe<T>` — capacity 1

- `write(msg)` overwrites any previous unread message in the slot.
- Used between adjacent stages in the common AST path where each input event yields at most one output message.

### `QueuePipe<T>` — FIFO

- Backed by `ArrayDeque<T>`.
- Used for `LineSplitterFilter` output (1→N) and `ViolationSink` (file may yield many violations).

## Testing

`PipeTest`, `SingletonPipeTest`, `QueuePipeTest` verify: write/read order, drain after close, hasNext consistency, capacity behaviour for SingletonPipe.
