# Data Model: Pipe-and-Filter Refactoring of Metrics + Sizes Slice

**Date**: 2026-05-09
**Scope**: Internal data flowing across pipes between filters in the slice. No persistence; all entities are in-memory immutable values used during a single per-file Checkstyle traversal.

## Core Infrastructure Types

### `Pipe<T>`

| Method | Signature | Behaviour |
|--|--|--|
| `write` | `void write(T message)` | Producer writes one message. Order preserved. |
| `read` | `T read()` | Consumer reads next message; returns `null` if drained. |
| `hasNext` | `boolean hasNext()` | True iff a message is available. |
| `close` | `void close()` | Marks pipe as no-more-input; subsequent reads drain remaining buffer then return null. |

**Implementations**: `SingletonPipe<T>` (capacity 1), `QueuePipe<T>` (FIFO ArrayDeque-backed).
**Invariants**: pipe holds no reference to producing or consuming filter; pipe is unidirectional (no back-channel).

### `Filter<I, O>`

| Method | Signature | Behaviour |
|--|--|--|
| `process` | `void process(Pipe<I> in, Pipe<O> out)` | Reads zero or more messages from `in`, writes zero or more to `out`. Filter never holds a reference to other filters. |

**Invariants**: no field or constructor parameter typed `Filter`; private state confined to the filter; never calls Checkstyle execution-infrastructure methods (`AbstractCheck.log`, etc.).

### `Pipeline<HEAD, TAIL>`

| Field/Method | Description |
|--|--|
| `submit(HEAD msg)` | Driver entry point; pushes one head message and runs all stages in order. |
| `drain() : TAIL` | Returns next tail message or `null`. |
| `hasResults() : boolean` | True iff tail has messages. |
| `stages` | Ordered, immutable list of filter+pipe pairs. |

Built once via `PipelineBuilder.start().add(filter).add(filter)…build()`. Builder validates output(stage i) ≡ input(stage i+1) at compile time (generics) and runtime.

## Message Types (immutable value objects)

### `AstEvent`

| Field | Type | Notes |
|--|--|--|
| `node` | `DetailAST` | The AST node observed. Reference; treated as read-only by all filters. |
| `phase` | `Phase` enum | One of `BEGIN_TREE`, `VISIT`, `LEAVE`, `FINISH_TREE`. |

**Used by**: all 14 AST-based pipelines + 2 coupling pipelines.

### `FileLine`

| Field | Type | Notes |
|--|--|--|
| `lineNo` | `int` | 1-based line number. |
| `text` | `String` | Original line text (no trailing newline). |

**Used by**: `LineLengthCheck`, `FileLengthCheck` pipelines.

### `Measurement`

| Field | Type | Notes |
|--|--|--|
| `subject` | `DetailAST` (nullable) | Null for file-level checks. |
| `lineNo` | `int` | Reporting location. |
| `colNo` | `int` | Reporting location. |
| `value` | `int` | The measured quantity (count, length, complexity). Boxed only at message-construction boundary. |
| `messageKey` | `String` | Bundle key from `messages.properties`. |
| `args` | `Object[]` | Args for the message; defensively copied at construction. |

**Producer**: any measurement filter. **Consumer**: `ThresholdFilter`.

### `ViolationMessage`

| Field | Type | Notes |
|--|--|--|
| `line` | `int` | Reporting location. |
| `col` | `int` | Reporting location. |
| `messageKey` | `String` | Same as upstream `Measurement`. |
| `args` | `Object[]` | Defensively copied. |

**Producer**: `ThresholdFilter`. **Consumer**: `ViolationSink` → drained by driver.

## Filter Catalogue

### Common (5)

| Filter | I | O | Responsibility |
|--|--|--|--|
| `TokenFilter` | `AstEvent` | `AstEvent` | Forwards events whose `node.getType()` is in configured set. |
| `LineSplitterFilter` | `FileText` | `FileLine` | Splits file into per-line messages, 1-based. |
| `IgnorePatternFilter` | `FileLine` | `FileLine` | Drops lines matching configured regex (LineLength only). |
| `ThresholdFilter` | `Measurement` | `ViolationMessage` | Compares `value > max`; emits violation if exceeded. |
| `ViolationSink` | `ViolationMessage` | `ViolationMessage` | Terminal stage, buffers for driver drain. |

### Per-check measurement filters (16)

Owns the original measurement algorithm of the matching `*Check`. Inputs `AstEvent` (or `FileLine` for file-level). Outputs `Measurement`. Never logs, never compares thresholds.

```
metrics: BooleanExpression, ClassDataAbstractionCoupling, ClassFanOutComplexity,
         Cyclomatic, JavaNcss, NPath
sizes:   AnonInnerLength, ExecutableStatementCount, FileLength, LambdaBodyLength,
         LineLength, MethodCount, MethodLength, OuterTypeNumber, ParameterNumber,
         RecordComponentNumber
```

### Coupling helpers (2)

| Filter | I | O | Responsibility |
|--|--|--|--|
| `ImportTrackingFilter` | `AstEvent` | `AstEvent` | Observes `IMPORT`/`PACKAGE_DEF` events to maintain type-resolution context (passthrough). |
| `AbstractCouplingMeasurementFilter` | `AstEvent` | `Measurement` | Shared base for `ClassDataAbstractionCoupling` and `ClassFanOutComplexity` measurement filters; uses retained `AbstractClassCouplingCheck` helper for type resolution. |

## State Transitions (per file)

```
Driver.beginTree(rootAST)
  └─ pipeline.submit(AstEvent(root, BEGIN_TREE)); drain → log(*)

Driver.visitToken(ast)            (per token in declared set)
  └─ pipeline.submit(AstEvent(ast, VISIT)); drain → log(*)

Driver.leaveToken(ast)            (per token in declared set)
  └─ pipeline.submit(AstEvent(ast, LEAVE)); drain → log(*)

Driver.finishTree(rootAST)
  └─ pipeline.submit(AstEvent(root, FINISH_TREE)); drain → log(*)
```

File-level analogue (`AbstractFileSetCheck.processFiltered`):

```
Driver.processFiltered(file, fileText)
  └─ pipeline.submit(fileText); drain all → log(*)
```

## Validation Rules (encoded as ArchUnit + jQAssistant)

| Rule | Source | Statement |
|--|--|--|
| R1 | ArchUnit | `..checks.pipeline..` classes ⊄ `AbstractCheck`/`AbstractFileSetCheck` |
| R2 | ArchUnit | `..{metrics,sizes}.pipeline..` classes ⊄ `AbstractCheck`/`AbstractFileSetCheck` |
| R3 | ArchUnit | `Filter` implementations ⊄ callers of `AbstractCheck.log(..)` |
| R4 | ArchUnit | every concrete class in pipeline filter packages implements `Filter<?,?>` |
| R5/R6 | ArchUnit | `..{metrics,sizes}.pipeline..` depend only on `..checks.pipeline..`, JDK, allow-listed Checkstyle utility/AST types |
| R7 | ArchUnit | drivers depend on `..checks.pipeline..` and Checkstyle `api`, but invoke measurement filters only via `Pipeline` |
| R8 | ArchUnit | `..checks.pipeline..` ⊄ depend on `..{metrics,sizes}..` |
| R9 | ArchUnit | no filter has a field/ctor-param typed as another concrete `Filter` impl (only `Pipe<?>`) |
| R10 | ArchUnit | `api` ⊄ depend on `..checks.pipeline..` |
| Q1 | jQAssistant | filters' direct dependencies ⊆ {pipe, JDK, allow-listed AST types} |
| Q2 | jQAssistant | no measurement-filter class extends `AbstractCheck` |
| Q3 | jQAssistant | adjacency graph of (filter, in-pipe-type, out-pipe-type, downstream) — diagram source |
| Q4 | jQAssistant | filter dependency graph contains no cycles |
| Q5 | jQAssistant | no `INVOKES` edge from measurement filter to `AbstractCheck.log(..)` |
