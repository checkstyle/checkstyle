# Contract: Pipeline Messages

All four message types are **immutable value objects**: `final` class, `final` fields, populated only by constructor; arrays defensively copied on the constructor boundary.

## `AstEvent`

```java
public final class AstEvent {
    public enum Phase { BEGIN_TREE, VISIT, LEAVE, FINISH_TREE }
    private final DetailAST node;
    private final Phase phase;
    public AstEvent(DetailAST node, Phase phase) { ... }
    public DetailAST getNode() { return node; }
    public Phase getPhase() { return phase; }
}
```

**Invariants**: `node` may be `null` only if `phase` is `BEGIN_TREE` or `FINISH_TREE` and the framework dispatches without an AST. Otherwise non-null.

## `FileLine`

```java
public final class FileLine {
    private final int lineNo;     // 1-based
    private final String text;    // no trailing newline
    public FileLine(int lineNo, String text) { ... }
    public int getLineNo() { return lineNo; }
    public String getText() { return text; }
}
```

**Invariants**: `lineNo >= 1`; `text != null`.

## `Measurement`

```java
public final class Measurement {
    private final DetailAST subject;   // nullable for file-level
    private final int lineNo;
    private final int colNo;
    private final int value;
    private final String messageKey;
    private final Object[] args;       // defensively copied
    public Measurement(DetailAST subject, int lineNo, int colNo,
                       int value, String messageKey, Object... args) { ... }
}
```

**Invariants**: `messageKey` is a key present in `messages.properties`; `args` length matches the format string.

## `ViolationMessage`

```java
public final class ViolationMessage {
    private final int line;
    private final int col;
    private final String messageKey;
    private final Object[] args;
    public ViolationMessage(int line, int col, String messageKey, Object... args) { ... }
}
```

**Invariants**: produced only by `ThresholdFilter`; consumed by `ViolationSink`; drained by the driver to `log(line, col, messageKey, args)`.

## Immutability enforcement

ArchUnit rule (custom): every class in `..checks.pipeline.message..` is `final`, has only `private final` non-static fields, and exposes only getters (no setters, no public mutable collection fields).

## Equality and hashing

Messages do not need to satisfy `equals`/`hashCode` contracts because they are flow-only values; tests assert on individual fields rather than message equality.
