# Contract: `Filter<I, O>`

## Java surface

```java
package com.puppycrawl.tools.checkstyle.checks.pipeline;

public interface Filter<I, O> {
    void process(Pipe<I> in, Pipe<O> out);
}
```

## Invariants

1. **No sibling reference**: a filter has no field or constructor parameter typed as another concrete `Filter` implementation. Only `Pipe<?>` and scalar configuration are allowed.
2. **No framework upcalls**: a filter does not call `AbstractCheck.log(..)`, does not access `MessageDispatcher`, does not interact with `TreeWalker`/`Checker`.
3. **Bounded dependencies**: measurement filters depend only on
   - JDK,
   - `..checks.pipeline..`,
   - the allow-listed Checkstyle utility/AST types: `DetailAST`, `TokenTypes`, `FullIdent`, `ScopeUtil`, `CommonUtil`, `CheckUtil`, `AnnotationUtil`.
4. **Local state only**: any state held by the filter is private to that filter and lives only across the duration of `process` plus inter-callback retention for context-tracking filters (e.g. `ImportTrackingFilter`). State never escapes the filter.
5. **Single responsibility**: each filter performs exactly one of {select, measure, compare, sink}.
6. **Pipe asymmetry**: filter may only call `read`/`hasNext`/`close` on `in` and only `write`/`close` on `out`. Reading from `out` or writing to `in` is a build-time / review-time error.

## Filter taxonomy

| Kind | Reads | Writes | Examples |
|--|--|--|--|
| Select | `AstEvent`/`FileLine` | same type | `TokenFilter`, `IgnorePatternFilter`, `ImportTrackingFilter` (passthrough side-effect) |
| Split | `FileText` | `FileLine` | `LineSplitterFilter` |
| Measure | `AstEvent`/`FileLine` | `Measurement` | 16 per-check measurement filters |
| Compare | `Measurement` | `ViolationMessage` | `ThresholdFilter` |
| Sink | `ViolationMessage` | `ViolationMessage` (drain) | `ViolationSink` |

## Testing

Each filter has a unit test that feeds a hand-built input message stream and asserts the output stream. No Checkstyle execution context required.

## Enforcement

- ArchUnit rules R3, R4, R5, R6, R8, R9 (see `data-model.md` validation table).
- jQAssistant queries Q1, Q2, Q4, Q5.
