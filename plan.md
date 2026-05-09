# Task 2 Implementation Plan — Pipe-and-Filter Refactoring of the Metrics + Sizes Slice

Group: THE GROUP (Karim Hariri, Mohamed Attia Eid Attia Eid, Zaid Hardan)
Target architecture (per group registration): **Pipe-and-Filter Architecture**
Slice in scope: all checks under `com.puppycrawl.tools.checkstyle.checks.metrics` and `com.puppycrawl.tools.checkstyle.checks.sizes` (16 concrete checks + 1 shared abstract base from the metrics package).

This document is the implementation-oriented plan. The companion report (`report.md`) describes the same refactoring in the academic style required by the assignment.

---

## 1. Architecture Understanding

### 1.1 Why Pipe-and-Filter for this slice
Pipe-and-Filter (P&F) decomposes a computation into independent processing **filters** connected by typed **pipes**. Each filter has a single responsibility and only knows about the messages on its input pipe and the messages it places on its output pipe. There is no shared mutable state across filters; coordination is exclusively the data flowing through the pipes; control flow is unidirectional.

For this assignment the style is the only one accepted (Task 2 clarification + group registration). The Metrics and Sizes checks fit naturally into a linear data-flow because every check follows the same shape:

1. receive a stream of AST events (or file lines for the two file-level checks);
2. select the events that this check cares about (token type / line);
3. measure something from the selected events (count, length, complexity);
4. compare the measurement against a configured threshold;
5. emit a violation if the threshold is crossed.

These five concerns become five filters in the pipeline. The boundary with the rest of Checkstyle (Checker, TreeWalker) is preserved by a thin **Pipeline Driver** class that registers with the framework and feeds AST callbacks into the head of the pipeline. The driver is not a filter — it does not read or transform messages — it is the source/sink mount that keeps the slice plug-compatible with the unchanged Checkstyle plug-in registration mechanism.

### 1.2 Why this is not the original architecture
Task 1 recovered Checkstyle's primary style as a Plug-in Framework with a per-file sequential traversal. The original design lets each `*Check` class own everything: AST traversal handling, measurement, threshold check, and violation emission. There are no pipes; control flow is `TreeWalker -> AbstractCheck.visitToken()`; there is shared mutable state inside the check (counters, scope stacks); there is no clear stage isolation.

The refactored slice replaces that single coupled class with a chain of independent filters connected by typed pipes. After refactoring, the outer Pipeline Driver is the only class that remains in the original package; the measurement, threshold, and emission concerns move into separate filter classes.

### 1.3 What the assignment forbids and how the design avoids it
- *Shared mutable state* — every filter's state is private and lives only inside that filter; pipes carry **immutable value messages** (`AstEvent`, `Measurement`, `FileLine`, `ViolationMessage`).
- *Other types of coordination* — there are no callbacks, listeners, or event buses; only `pipe.write(msg)` from a producing filter and `pipe.read()` from the consuming filter.
- *Layered shortcuts* — filters never call into the Checkstyle `api` package; the boundary coupling is contained in the Pipeline Driver only. ArchUnit rules listed in §10 enforce this.

---

## 2. Pipeline Structure

### 2.1 Pipe definitions

```
package com.puppycrawl.tools.checkstyle.checks.pipeline.pipe;

public interface Pipe<T> {
    void write(T message);
    T read();              // returns null if drained
    boolean hasNext();
    void close();
}
```

Two concrete implementations:
- `SingletonPipe<T>` — single-message slot; suitable for synchronous, in-thread pipelines (which is all we need; Checkstyle is single-threaded per file).
- `QueuePipe<T>` — `ArrayDeque`-backed FIFO buffer; used where a filter emits multiple messages per input (e.g. line splitter).

Pipes are **unidirectional** by interface: nothing in `Pipe<T>` lets a downstream filter push back to an upstream filter. There is no reference to the producer in the pipe object.

### 2.2 Filter base interface

```
public interface Filter<I, O> {
    void process(Pipe<I> in, Pipe<O> out);
}
```

Each filter reads zero or more messages from `in`, transforms them, and writes zero or more messages to `out`. A filter never holds a reference to another filter. A filter may keep its own private state (a counter, a stack used inside `process`) but that state never escapes the filter.

### 2.3 Pipeline composer

```
public final class Pipeline<HEAD, TAIL> {
    private final Pipe<HEAD> head;
    private final Pipe<TAIL> tail;
    private final List<FilterStage<?,?>> stages; // ordered

    public void submit(HEAD msg) { head.write(msg); runStages(); }
    public TAIL drain() { return tail.read(); }
    public boolean hasResults() { return tail.hasNext(); }
}
```

A `Pipeline.Builder` connects filters in declaration order and validates that the output type of stage *i* matches the input type of stage *i+1* (compile-time generics + runtime assertion in the builder). A built pipeline is immutable.

### 2.4 Message types (immutable value objects)

| Message | Carries | Used by |
|--|--|--|
| `AstEvent` | `DetailAST node`, `AstEvent.Phase phase` (`BEGIN_TREE`, `VISIT`, `LEAVE`, `FINISH_TREE`) | All AST-based pipelines |
| `FileLine` | `int lineNo`, `String text` | LineLengthPipeline, FileLengthPipeline |
| `Measurement` | `DetailAST subject` (nullable for file-level), `int lineNo`, `int colNo`, `Number value`, `Object[] context` | Output of every measurement filter |
| `ViolationMessage` | `int line`, `int col`, `String messageKey`, `Object[] args` | Output of threshold filter, consumed by sink |

All four are `final` classes with `final` fields; constructors are the only way to populate them.

### 2.5 Common filters

| Filter | Reads | Writes | Responsibility |
|--|--|--|--|
| `TokenFilter` | `AstEvent` | `AstEvent` | Forwards events whose `node.getType()` is in the configured token-type set; drops the rest. |
| `LineSplitterFilter` | `FileText` (one input) | `FileLine` (many outputs) | Decomposes a `FileText` into per-line messages (1-based line numbers). |
| `ThresholdFilter` | `Measurement` | `ViolationMessage` | Compares `Measurement.value` to the configured ceiling; if crossed, builds a `ViolationMessage` with the key/args supplied by the upstream measurement filter. |
| `ViolationSink` | `ViolationMessage` | `ViolationMessage` (terminal — passes to drain) | Final stage; the Pipeline Driver drains this. |

### 2.6 Per-check measurement filters
Each of the 16 checks owns one measurement filter class. Existing measurement logic is moved into that class **byte-for-byte** at the algorithm level (the rules say "check logic must not be rewritten"). The only change inside is that the filter does not call `log(...)` directly; it emits a `Measurement` message that the downstream `ThresholdFilter` later turns into a `ViolationMessage` (or into nothing).

### 2.7 The five canonical pipelines

#### 2.7.1 AST-based simple measurement (12 of 16)
Used by: `BooleanExpressionComplexity`, `CyclomaticComplexity`, `JavaNCSS`, `NPathComplexity`, `AnonInnerLength`, `ExecutableStatementCount`, `LambdaBodyLength`, `MethodCount`, `MethodLength`, `OuterTypeNumber`, `ParameterNumber`, `RecordComponentNumber`.

```
[AstEvent] -> TokenFilter -> [AstEvent] -> <X>MeasurementFilter -> [Measurement]
            -> ThresholdFilter -> [ViolationMessage] -> ViolationSink
```

#### 2.7.2 AST-based coupling pipelines (2 of 16)
Used by: `ClassDataAbstractionCoupling`, `ClassFanOutComplexity` — both share an abstract measurement base in the original code (`AbstractClassCouplingCheck`).

```
[AstEvent] -> TokenFilter -> [AstEvent]
            -> ImportTrackingFilter   -> [AstEvent] (passthrough; updates package context)
            -> CouplingMeasurementFilter -> [Measurement]
            -> ThresholdFilter -> [ViolationMessage] -> ViolationSink
```

`ImportTrackingFilter` is the only filter that observes `IMPORT`/`PACKAGE_DEF` token events to keep the type-resolution context required by both coupling checks. It is shared between the two coupling pipelines through plain composition (a constructor argument), not inheritance — keeping each pipeline's filter chain explicit.

#### 2.7.3 File-level pipelines (2 of 16)
Used by: `FileLengthCheck`, `LineLengthCheck`.

```
[FileText] -> LineSplitterFilter -> [FileLine] -> <X>MeasurementFilter -> [Measurement]
            -> ThresholdFilter -> [ViolationMessage] -> ViolationSink
```

For `LineLengthCheck` an additional `IgnorePatternFilter` sits between the splitter and the measurement filter to drop lines matching the configured pattern (default `^(package|import) .*`). It is a normal filter (`Filter<FileLine, FileLine>`).

For `FileLengthCheck` the measurement filter does not need every line — it only needs the count, so it implements `process` by counting `in.read()` calls until the pipe drains and emits one `Measurement` at the end. This still respects the contract: the filter only sees the input pipe; nothing is shared.

### 2.8 Pipeline Drivers
Each of the 16 outer `*Check.java` files keeps its original fully-qualified class name (so XML configs continue to work) and continues to extend `AbstractCheck` or `AbstractFileSetCheck` (so TreeWalker / Checker can register it). The class becomes a thin **Pipeline Driver** with:

- one field: `private Pipeline<AstEvent, ViolationMessage> pipeline;` (or `Pipeline<FileText, ViolationMessage>` for file-level);
- `init()` builds the pipeline (`PipelineBuilder.start().add(new TokenFilter(...)).add(new XMeasurementFilter(...)).add(new ThresholdFilter(max, MSG_KEY)).add(new ViolationSink()).build()`);
- `visitToken(ast)`/`leaveToken(ast)` translate to `pipeline.submit(new AstEvent(ast, VISIT))` and similarly for LEAVE; after each submit the driver calls `drain()` and forwards any `ViolationMessage` to `log()`;
- configuration setters (`setMax`, `setIgnorePattern`, etc.) keep the same signatures and Javadoc that Checkstyle's documentation generator already targets.

The driver does **no measurement and no threshold comparison**. It is purely the source-and-sink mount.

---

## 3. Filter Responsibilities Reference Table

| Filter class | Package | Reads | Writes | Owns logic from |
|--|--|--|--|--|
| `Pipe<T>`, `SingletonPipe<T>`, `QueuePipe<T>` | `checks.pipeline.pipe` | — | — | new |
| `Filter<I,O>` | `checks.pipeline` | — | — | new |
| `Pipeline<H,T>`, `PipelineBuilder` | `checks.pipeline` | — | — | new |
| `AstEvent`, `FileLine`, `Measurement`, `ViolationMessage` | `checks.pipeline.message` | — | — | new |
| `TokenFilter` | `checks.pipeline.filter` | `AstEvent` | `AstEvent` | new (config-driven) |
| `LineSplitterFilter` | `checks.pipeline.filter` | `FileText` | `FileLine` | extracted from `LineLengthCheck.processFiltered` (loop body) |
| `IgnorePatternFilter` | `checks.pipeline.filter` | `FileLine` | `FileLine` | extracted from `LineLengthCheck.processFiltered` (pattern guard) |
| `ThresholdFilter` | `checks.pipeline.filter` | `Measurement` | `ViolationMessage` | extracted from every `*Check`'s `if (value > max) log(...)` |
| `ViolationSink` | `checks.pipeline.filter` | `ViolationMessage` | `ViolationMessage` (drain) | new |
| `BooleanExpressionMeasurementFilter` | `checks.metrics.pipeline` | `AstEvent` | `Measurement` | `BooleanExpressionComplexityCheck.visitToken` core |
| `ClassDataAbstractionCouplingMeasurementFilter` | `checks.metrics.pipeline` | `AstEvent` | `Measurement` | `ClassDataAbstractionCouplingCheck` core (via shared coupling base) |
| `ClassFanOutComplexityMeasurementFilter` | `checks.metrics.pipeline` | `AstEvent` | `Measurement` | `ClassFanOutComplexityCheck` core |
| `CyclomaticMeasurementFilter` | `checks.metrics.pipeline` | `AstEvent` | `Measurement` | `CyclomaticComplexityCheck` core |
| `JavaNcssMeasurementFilter` | `checks.metrics.pipeline` | `AstEvent` | `Measurement` | `JavaNCSSCheck` core |
| `NPathMeasurementFilter` | `checks.metrics.pipeline` | `AstEvent` | `Measurement` | `NPathComplexityCheck` core |
| `AbstractCouplingMeasurementFilter` | `checks.metrics.pipeline` | `AstEvent` | `Measurement` | shared coupling helpers from `AbstractClassCouplingCheck` |
| `ImportTrackingFilter` | `checks.metrics.pipeline` | `AstEvent` | `AstEvent` | imports/package context tracking (formerly inside the coupling base) |
| `AnonInnerLengthMeasurementFilter` | `checks.sizes.pipeline` | `AstEvent` | `Measurement` | `AnonInnerLengthCheck` core |
| `ExecutableStatementCountMeasurementFilter` | `checks.sizes.pipeline` | `AstEvent` | `Measurement` | `ExecutableStatementCountCheck` core |
| `LambdaBodyLengthMeasurementFilter` | `checks.sizes.pipeline` | `AstEvent` | `Measurement` | `LambdaBodyLengthCheck` core |
| `MethodCountMeasurementFilter` | `checks.sizes.pipeline` | `AstEvent` | `Measurement` | `MethodCountCheck` core |
| `MethodLengthMeasurementFilter` | `checks.sizes.pipeline` | `AstEvent` | `Measurement` | `MethodLengthCheck` core |
| `OuterTypeNumberMeasurementFilter` | `checks.sizes.pipeline` | `AstEvent` | `Measurement` | `OuterTypeNumberCheck` core |
| `ParameterNumberMeasurementFilter` | `checks.sizes.pipeline` | `AstEvent` | `Measurement` | `ParameterNumberCheck` core |
| `RecordComponentNumberMeasurementFilter` | `checks.sizes.pipeline` | `AstEvent` | `Measurement` | `RecordComponentNumberCheck` core |
| `FileLengthMeasurementFilter` | `checks.sizes.pipeline` | `FileLine` | `Measurement` | `FileLengthCheck` core |
| `LineLengthMeasurementFilter` | `checks.sizes.pipeline` | `FileLine` | `Measurement` | `LineLengthCheck` core (length calculation only; ignore-pattern and tab expansion stay where they belong by responsibility — see below) |

Tab-width expansion in `LineLengthCheck` stays inside `LineLengthMeasurementFilter` (it is part of "measure the line"); the ignore-pattern guard moves to its own filter (it is "select the lines we measure"). This split matches the P&F rule that each filter has one responsibility.

---

## 4. Execution Stages and Data Flow

### 4.1 Per-file lifecycle (AST checks)
1. `Checker.process(file)` is unchanged.
2. `TreeWalker.processFiltered(file, fileText)` parses to `DetailAST` and walks the tree (unchanged).
3. For each registered check, `TreeWalker` calls `beginTree`, then `visitToken` / `leaveToken` per relevant token, then `finishTree` — unchanged.
4. **New behaviour starts here.** The Pipeline Driver translates each callback into an `AstEvent` and writes it to the pipeline head; immediately after each submit it drains the tail and forwards every `ViolationMessage` to `log(...)`.
5. The pipe between `TokenFilter` and the measurement filter holds at most one `AstEvent` at a time (singleton pipe). The pipe between measurement and threshold holds at most one `Measurement`. The sink pipe is a `QueuePipe<ViolationMessage>` because some checks emit several violations per file.

### 4.2 Per-file lifecycle (file-level checks)
1. `Checker.process(file)` is unchanged.
2. `Checker` invokes `processFiltered(file, fileText)` on the file-set check (the Pipeline Driver). The driver submits the `FileText` to the pipeline head and drains the tail.
3. Inside the pipeline, the splitter emits one `FileLine` per line; the ignore-pattern filter (LineLength only) drops lines that should be skipped; the measurement filter computes the per-line or per-file value; the threshold filter compares against `max`; the sink collects `ViolationMessage`s.
4. The driver forwards each violation to `log(...)`.

### 4.3 Unidirectional flow guarantee
No filter has a reference to another filter; messages move strictly along the pipe chain. The `Pipeline.submit` method runs each stage in order; a stage's `process(in, out)` only sees its own two pipes. Because pipes are typed and unidirectional, the build fails if a filter tries to read from `out` or write to `in`.

---

## 5. File-by-File Modification Plan

### 5.1 New files (added)

```
src/main/java/com/puppycrawl/tools/checkstyle/checks/pipeline/
    Filter.java
    Pipeline.java
    PipelineBuilder.java
    pipe/
        Pipe.java
        SingletonPipe.java
        QueuePipe.java
    message/
        AstEvent.java
        FileLine.java
        Measurement.java
        ViolationMessage.java
    filter/
        TokenFilter.java
        LineSplitterFilter.java
        IgnorePatternFilter.java
        ThresholdFilter.java
        ViolationSink.java
    package-info.java
```

```
src/main/java/com/puppycrawl/tools/checkstyle/checks/metrics/pipeline/
    AbstractCouplingMeasurementFilter.java
    BooleanExpressionMeasurementFilter.java
    ClassDataAbstractionCouplingMeasurementFilter.java
    ClassFanOutComplexityMeasurementFilter.java
    CyclomaticMeasurementFilter.java
    ImportTrackingFilter.java
    JavaNcssMeasurementFilter.java
    NPathMeasurementFilter.java
    package-info.java
```

```
src/main/java/com/puppycrawl/tools/checkstyle/checks/sizes/pipeline/
    AnonInnerLengthMeasurementFilter.java
    ExecutableStatementCountMeasurementFilter.java
    FileLengthMeasurementFilter.java
    LambdaBodyLengthMeasurementFilter.java
    LineLengthMeasurementFilter.java
    MethodCountMeasurementFilter.java
    MethodLengthMeasurementFilter.java
    OuterTypeNumberMeasurementFilter.java
    ParameterNumberMeasurementFilter.java
    RecordComponentNumberMeasurementFilter.java
    package-info.java
```

### 5.2 Existing files (modified, **same name and package preserved**)
Each of the 16 outer `*Check.java` files is rewritten as a Pipeline Driver. The original measurement code is moved out (into the matching filter); the configuration setters and Javadoc stay; `getDefaultTokens / getAcceptableTokens / getRequiredTokens` stay (they tell TreeWalker which tokens to dispatch — that decision lives at the framework boundary, not inside the pipeline).

| File | Change |
|--|--|
| `metrics/BooleanExpressionComplexityCheck.java` | rewrite as driver |
| `metrics/ClassDataAbstractionCouplingCheck.java` | rewrite as driver |
| `metrics/ClassFanOutComplexityCheck.java` | rewrite as driver |
| `metrics/CyclomaticComplexityCheck.java` | rewrite as driver |
| `metrics/JavaNCSSCheck.java` | rewrite as driver |
| `metrics/NPathComplexityCheck.java` | rewrite as driver |
| `metrics/AbstractClassCouplingCheck.java` | retained as a private helper used only by the two coupling drivers; measurement bodies have been pulled out into the coupling measurement filter. (Kept because the assignment forbids "rewriting check logic" — the type-resolution helpers count as logic.) |
| `sizes/AnonInnerLengthCheck.java` | rewrite as driver |
| `sizes/ExecutableStatementCountCheck.java` | rewrite as driver |
| `sizes/FileLengthCheck.java` | rewrite as driver (file-level) |
| `sizes/LambdaBodyLengthCheck.java` | rewrite as driver |
| `sizes/LineLengthCheck.java` | rewrite as driver (file-level) |
| `sizes/MethodCountCheck.java` | rewrite as driver |
| `sizes/MethodLengthCheck.java` | rewrite as driver |
| `sizes/OuterTypeNumberCheck.java` | rewrite as driver |
| `sizes/ParameterNumberCheck.java` | rewrite as driver |
| `sizes/RecordComponentNumberCheck.java` | rewrite as driver |

### 5.3 Files that must **not** change
Everything outside the slice. In particular: `Checker`, `TreeWalker`, `JavaParser`, `ConfigurationLoader`, `PackageObjectFactory`, the rest of the `checks/*` packages, `messages.properties`, and all other `*.xml` configuration files.

---

## 6. Implementation Phases (sequential, each ends in a green build + byte-identical diff)

| Phase | Goal | Exit signal |
|--|--|--|
| 0 | Capture the pre-refactor baseline output. Run the original jar against `violation-sample/SampleAllViolations.java` and store the output. | `pre-refactor-output.txt` saved |
| 1 | Add the empty pipeline package skeleton (interfaces and two pipes only). No check is wired yet. | `mvn -DskipTests package` succeeds |
| 2 | Add common filters (`TokenFilter`, `ThresholdFilter`, `ViolationSink`, `LineSplitterFilter`, `IgnorePatternFilter`) plus message types. | Maven compiles; no existing test changes |
| 3 | Migrate `MethodLengthCheck` first as a pilot (smallest AST check). | Output diff against baseline is empty; full Maven test suite passes |
| 4 | Migrate the remaining nine size AST checks one at a time, in order: `AnonInnerLength`, `ParameterNumber`, `RecordComponentNumber`, `OuterTypeNumber`, `MethodCount`, `LambdaBodyLength`, `ExecutableStatementCount`. | After each: empty diff + green test suite |
| 5 | Migrate `FileLengthCheck` (file-level), then `LineLengthCheck` (file-level + ignore pattern). | Empty diff + green test suite |
| 6 | Migrate `BooleanExpressionComplexity`, `CyclomaticComplexity`, `NPathComplexity`, `JavaNCSS`. | Empty diff + green test suite |
| 7 | Migrate the two coupling checks last (most stateful: import tracking, package context). | Empty diff + green test suite |
| 8 | Add ArchUnit conformance tests (§10.1). | All ArchUnit rules green |
| 9 | Add jQAssistant rules (§10.2). | Constraint queries return 0 rows |
| 10 | Run scalability experiment (§12) and update `report.md` Section 10. | Graph + table regenerated |

A check is considered "migrated" when:
- the original measurement code lives in its measurement filter;
- the outer driver has zero `if (value > max)` and zero direct `log(...)` calls in its measurement methods (only `pipeline.drain()`-fed `log` in the violation forwarding loop);
- the diff against the Phase 0 baseline output is empty.

---

## 7. Required New Classes (rationale per class)

| Class | Architectural role | Why this class is necessary |
|--|--|--|
| `Pipe<T>` | Communication channel | The architecture mandates explicit pipes; without this interface the build cannot enforce unidirectional, typed message flow. |
| `SingletonPipe<T>` | Single-message channel | Used between adjacent filters in synchronous pipelines; smaller and clearer than a queue. |
| `QueuePipe<T>` | Buffered channel | Used where one input message yields many outputs (line splitter) or where the sink may collect several violations. |
| `Filter<I,O>` | Filter contract | The architecture mandates "independent processing stages"; a uniform contract makes that explicit. |
| `Pipeline<H,T>` + `PipelineBuilder` | Composition | The driver must build the chain once; the builder validates type-compatibility between stages at compile time. |
| `AstEvent`, `FileLine`, `Measurement`, `ViolationMessage` | Immutable messages | Pipes carry value messages; they cannot be the raw `DetailAST` because that would let downstream filters mutate framework state. |
| `TokenFilter` | Selection stage | First responsibility step of every AST pipeline; reused by all 14 AST drivers. |
| `LineSplitterFilter`, `IgnorePatternFilter` | Selection stages (file-level) | Same role for the two file-level pipelines. |
| `ThresholdFilter` | Comparison stage | Comparison is identical across 14 of the 16 checks; isolating it makes thresholds auditable. |
| `ViolationSink` | Termination stage | Gives the driver a single drain point and a clean place for ArchUnit's "only the sink emits violations" rule to attach to. |
| 16 measurement filters | Measurement stage | One per check; this is where the original algorithm lives. |
| 1 import-tracking filter + 1 abstract coupling filter | Selection + measurement (coupling) | The coupling checks need a small amount of cross-token context; isolating the context tracking into its own filter keeps the measurement filter focused on counting. |

Counts: **5** new core classes + **3** pipe classes + **4** message types + **5** common filters + **16** per-check measurement filters + **2** coupling helper filters = **35** new classes. The 16 outer drivers replace 16 existing files (no net file addition for them).

---

## 8. Validation Strategy

### 8.1 ArchUnit rules (in `src/test/java/.../architecture/PipeAndFilterArchitectureTest.java`)

| Rule | Statement | Why |
|--|--|--|
| R1 | classes in `..checks.pipeline..` should not extend `AbstractCheck` or `AbstractFileSetCheck` | core pipeline must be framework-free |
| R2 | classes in `..checks.metrics.pipeline..` and `..checks.sizes.pipeline..` should not extend `AbstractCheck` or `AbstractFileSetCheck` | measurement filters must be framework-free |
| R3 | classes implementing `Filter` should not call `AbstractCheck.log(..)` (method access check) | violations leave the slice only via the sink + driver |
| R4 | every concrete class in the pipeline filter packages should implement `Filter<?,?>` | enforces the filter contract |
| R5 | classes in `..checks.metrics.pipeline..` should depend only on classes in `..checks.pipeline..`, `java..`, and Checkstyle utility/AST types listed in an allow-list (`DetailAST`, `TokenTypes`, `FullIdent`, `ScopeUtil`, `CommonUtil`, `CheckUtil`, `AnnotationUtil`) | filters must not see Checkstyle execution infrastructure |
| R6 | same allow-list rule for `..checks.sizes.pipeline..` | symmetric |
| R7 | classes in `..checks.metrics..` outside `..pipeline..` (the drivers) should depend on `..checks.pipeline..` and Checkstyle `api`, but not on `..checks.metrics.pipeline..` measurement filters via direct field access — only through `Pipeline` | drivers stay thin |
| R8 | no class in `..checks.pipeline..` depends on any class in `..checks.metrics..` or `..checks.sizes..` | the core pipeline does not know its users |
| R9 | adjacency rule: no filter class has a field or constructor parameter typed as another concrete `Filter` implementation (only `Pipe<?>`) | filters never bind to siblings |
| R10 | classes in `api` should not depend on `..checks.pipeline..` | preserves the original Checkstyle plug-in isolation |

### 8.2 jQAssistant queries (in `jqassistant/rules/pipe-and-filter.xml`)

- **Q1** — All filters and their direct dependencies (expected: only port/pipe types, JDK, allow-listed AST types).
- **Q2** — Find any class in `..checks.metrics.pipeline..` or `..checks.sizes.pipeline..` that extends `AbstractCheck` (expected: 0 rows).
- **Q3** — Adjacency graph: for each pipeline, list (filter, input-pipe-type, output-pipe-type, downstream-filter); used to render the data-flow diagram for the report.
- **Q4** — Find any cycle in the filter dependency graph (expected: 0 rows; flow must be acyclic).
- **Q5** — Find any direct `INVOKES` edge from a measurement filter to `AbstractCheck.log(..)` (expected: 0 rows).

### 8.3 Output equivalence
Maintained automatically by the per-phase diff against the saved baseline (`pre-refactor-output.txt`). The diff is run after every check is migrated and after every architectural change. A non-empty diff blocks progress.

### 8.4 Structurizr / C4 updates
Three C4 diagrams need updating in `structurizr/workspace.dsl`:
- **L2 (containers)** — replace the "Hexagonal Slice" container with "Pipe-and-Filter Slice".
- **L3 (components)** — show the four common filters, the per-check measurement filter cluster, and the driver layer with arrows representing typed pipes.
- **L4 (code, exemplar)** — pick `MethodLengthCheck` and show the four-filter chain plus the driver.

---

## 9. Testing Strategy

### 9.1 Existing tests
The full Maven test suite (`mvn clean test`) must keep passing without modification. The drivers retain class names, FQNs, configuration property names, message keys, default tokens, and Javadoc — Checkstyle's own tests should not be able to tell the difference.

### 9.2 New tests
- `PipeTest`, `QueuePipeTest`, `SingletonPipeTest` — sanity tests for write/read/drain/close.
- `PipelineBuilderTest` — verifies type-compatibility checking and immutability.
- `TokenFilterTest`, `ThresholdFilterTest`, `ViolationSinkTest`, `LineSplitterFilterTest`, `IgnorePatternFilterTest` — unit tests on each common filter (no Checkstyle context required).
- `<X>MeasurementFilterTest` for every per-check filter — feed a hand-built `AstEvent` stream, assert the `Measurement` stream. Because the measurement filters are framework-free, these run without booting Checkstyle.
- `PipeAndFilterArchitectureTest` — the ArchUnit rule set above.
- `RegressionDiffTest` — re-runs the jar over `violation-sample/SampleAllViolations.java` and asserts the output equals the pinned baseline.

### 9.3 Per-check fire test
A short integration test asserts that each of the 16 checks still fires at least once on the bundled sample input file (catches "filter accidentally swallows everything" regressions).

---

## 10. Performance Evaluation Strategy

Same procedure and same five projects as Task 1 Part B (so the new graph can be plotted on the same axes):

| Project | Purpose |
|--|--|
| minimal-json | tiny baseline, JVM startup dominates |
| javapoet | small library, healthy AST diversity |
| gs-core | mid-sized library |
| jgrapht-core | mid/large, many small files |
| Apache Calcite (core) | beyond the knee from Task 1 |

For each project: 1 warm-up + 3 timed runs, mean and 95% CI per project. Both the original jar (saved as `baseline/checkstyle-original.jar`) and the refactored jar are run with the same `bench-config.xml` (the 16 metrics + sizes checks at default thresholds). Wall-clock time recorded with millisecond precision.

Expected outcome: the per-message overhead is two virtual calls per AST event (driver -> head pipe -> token filter; measurement -> threshold), all in the same thread; Java's JIT will inline these after warm-up. Expected delta is within ±10% noise on every project. The exact figures are filled in at Phase 10.

Threats to validity (recorded in the report):
- single host, single JVM version;
- wall-clock instead of JMH (acceptable for second-scale measurements; JMH listed as "what we would do differently");
- noisy desktop OS — mitigated by 3 runs and CI plots.

---

## 11. Risks and Mitigations

| Risk | Likelihood | Impact | Mitigation |
|--|--|--|--|
| Coupling checks rely on subtle import/scope state that is hard to model as a stream | Medium | High | Kept `AbstractClassCouplingCheck` as private helper (its logic was non-trivial); migration done in Phase 7 last so all simpler patterns are stabilised first |
| Measurement filters become a thin wrapper around `DetailAST` (filter is "AST passes through") | Low | Medium | The filter is justified by its **interface** (input `AstEvent`, output `Measurement`) not by code volume. The architecture is about composition + flow, not lines of code. |
| Tab-width handling in `LineLengthCheck` couples to the framework's `tabWidth` property | Medium | Low | Tab width is read on the driver and passed into the measurement filter constructor; the filter has no `getTabWidth()` upcall |
| Output ordering changes by accident (drain order != original log order) | Low | High | The driver drains the sink immediately after each `submit`, preserving the original "log violations as you find them" sequence. |
| Performance regression from boxing of `int` into `Number` in `Measurement` | Low | Low | Use `int` field directly inside `Measurement`; documented; can be revisited if benchmark moves outside ±10% noise |
| ArchUnit allow-list of utility classes (`DetailAST`, `TokenTypes`, `FullIdent`, `ScopeUtil`, `CommonUtil`, `CheckUtil`, `AnnotationUtil`) drifts | Low | Low | Allow-list is a single test constant; pull-request review process catches additions |

---

## 12. Report Update Strategy

The companion `report.md` is rewritten to follow the heading hierarchy and section ordering of `docs/ref/SENG 326 TASK 2 REPORT EXAMPLE.pdf`, but with all architectural content describing Pipe-and-Filter:

- Section 1 — Background on Checkstyle (kept similar, framework-neutral).
- Section 2 — What Pipe-and-Filter Is, why we chose it (constraint: it is the registered architecture).
- Section 3 — The 16 checks in scope.
- Section 4 — The new pipeline infrastructure classes.
- Section 5 — How the refactoring was done, step by step.
- Section 6 — Complete class mapping table + diagrams.
- Section 7 — Verification (regression diff, full test suite, ArchUnit, jQAssistant).
- Section 8 — Hard constraints (one row per assignment requirement).
- Section 9 — Lessons learned.
- Section 10 — Part B performance experiment.
- Section 11 — Glossary.
- Section 12 — References.
- Section 13 — Appendix (PlantUML / DSL sources, all in textual form per the assignment clarification).

After all content is final and verified, the humanizer skill (cloned to `~/.claude/skills/humanizer`) is run as a final pass on the report prose only — it must not touch class names, code blocks, table cells, diagrams, or appendices.

---

## 13. Definition of Done

- Empty `diff` between pre- and post-refactor output on `SampleAllViolations.java`.
- `mvn clean test` returns 0 failures, 0 errors.
- All ArchUnit rules R1–R10 green.
- All five jQAssistant constraint queries return 0 rows; Q3 (data-flow graph) reproduces the diagram in the report.
- `report.md` exists and matches the example report's section structure.
- Performance graph regenerated; the original-vs-refactored bars are within ±10% on every project.
- The slice is plug-compatible — `google_checks.xml` and any user XML continues to load and run with no edits.
