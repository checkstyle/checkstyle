# Research: Pipe-and-Filter Refactoring of Metrics + Sizes Slice

**Date**: 2026-05-09
**Status**: Complete (no NEEDS CLARIFICATION outstanding)

## R1. Pipe granularity — Singleton vs Queue

**Decision**: Provide two implementations of `Pipe<T>`: `SingletonPipe<T>` (single-slot) and `QueuePipe<T>` (FIFO ArrayDeque).
**Rationale**: 14 of 16 checks generate at most one in-flight `AstEvent` and one `Measurement` per `submit`; a singleton slot avoids per-event allocation and matches Checkstyle's per-file single-thread model. Splitter (1→N FileLine) and sink (multiple ViolationMessages per file) require buffering.
**Alternatives considered**:
- Always-queue: simpler code, but allocates ArrayDeque nodes per event for the common path.
- Always-singleton: cannot represent splitter or sink without external buffering, breaking the "filter only sees its two pipes" rule.

## R2. Message immutability

**Decision**: All four message types (`AstEvent`, `FileLine`, `Measurement`, `ViolationMessage`) are `final` classes with `final` fields, populated only via constructors.
**Rationale**: Pipes must not be a back-channel for shared mutable state; immutability prevents downstream filters from mutating `DetailAST`-owned state through accident. ArchUnit rule encodes this.
**Alternatives considered**:
- Records: equivalent semantics; chosen against because Checkstyle baseline targets Java 8 in some downstream artifacts and records require Java 16+. Final classes with final fields work on the existing toolchain.
- Mutable DTOs: rejected — defeats the architecture.

## R3. Where does AST traversal live?

**Decision**: AST traversal (`getDefaultTokens`/`getAcceptableTokens`/`getRequiredTokens`) stays on the driver — `TreeWalker` decides which tokens to dispatch. The driver translates each callback into an immutable `AstEvent`.
**Rationale**: Token-set declaration is part of the framework registration contract; moving it into a filter would require the filter to know about `TreeWalker`, breaking the framework-isolation rule (R5/R6).
**Alternatives considered**:
- Filter-driven token sets: requires filters to publish token-set metadata to TreeWalker → upcall into framework. Rejected.

## R4. Threshold comparison — shared vs per-check

**Decision**: A single `ThresholdFilter` consumes any `Measurement` and emits `ViolationMessage` when `value > max`; the message key and args are carried inside `Measurement.context`.
**Rationale**: 14 of 16 checks have the identical `if (value > max) log(key, args)` shape. Keeping the comparison in one filter makes the threshold contract auditable and removes duplicated code.
**Alternatives considered**:
- Per-check threshold filter: code duplication; harder to enforce consistency.
- Inline comparison in measurement filter: violates single-responsibility — measurement filter would also know thresholds.

## R5. Coupling-checks state

**Decision**: A dedicated `ImportTrackingFilter` observes `IMPORT` and `PACKAGE_DEF` events and forwards everything else unchanged. The two coupling measurement filters consume the (passthrough-augmented) event stream and rely on type-resolution helpers from a retained `AbstractClassCouplingCheck` helper.
**Rationale**: Coupling checks need package + import context to resolve type names; this is selection-stage logic. Splitting it from measurement keeps measurement filters focused. Retaining `AbstractClassCouplingCheck` as a private helper honours "do not rewrite check logic".
**Alternatives considered**:
- Cross-filter shared map: violates "no shared mutable state".
- Stuffing context into every `AstEvent`: bloats the universal message type for two checks.

## R6. File-level pipelines

**Decision**: File-level checks (`FileLengthCheck`, `LineLengthCheck`) use a `LineSplitterFilter` (input `FileText`, output many `FileLine`). LineLength inserts an `IgnorePatternFilter` between splitter and measurement.
**Rationale**: Splitting and pattern-matching are selection responsibilities; isolating them keeps measurement filters single-purpose.
**Alternatives considered**:
- Single combined "FileLevelMeasurementFilter": couples splitting, ignore, and measurement.

## R7. Tab-width handling in `LineLengthCheck`

**Decision**: Driver reads `tabWidth` framework property and passes it to `LineLengthMeasurementFilter` constructor.
**Rationale**: Filter must not call back into framework; configuration is injected at pipeline construction.
**Alternatives considered**:
- Embed in `FileLine`: pollutes the message for one check.
- Static accessor: shared mutable state.

## R8. Driver-side drain ordering

**Decision**: Driver drains the sink immediately after each `submit` and forwards each `ViolationMessage` to `log(...)` in order.
**Rationale**: Original Checkstyle logs violations as they are encountered; deferred drain (e.g., end-of-file) would re-order violations and break baseline equivalence.
**Alternatives considered**:
- End-of-tree drain: simpler driver, breaks output ordering.

## R9. Migration order

**Decision**: `MethodLengthCheck` first (pilot), then remaining size AST checks, then file-level (`FileLength`, `LineLength`), then complexity checks (BooleanExpr, Cyclomatic, NPath, JavaNCSS), then coupling checks last.
**Rationale**: Easy → hard. Coupling checks have cross-token state; doing them last lets simpler patterns stabilise the infrastructure first.

## R10. Verification toolchain

**Decision**: ArchUnit (R1–R10 in `PipeAndFilterArchitectureTest`) for compile-test enforcement; jQAssistant (Q1–Q5 in `pipe-and-filter.xml`) for graph-level queries; baseline byte-diff (`RegressionDiffTest`) for output equivalence.
**Rationale**: Triple coverage — type/inheritance (ArchUnit), dependency graph (jQAssistant), behavioural (diff). Each catches a class of regressions the others miss.

## R11. Performance methodology

**Decision**: Same five projects as Task 1 Part B (minimal-json, javapoet, gs-core, jgrapht-core, Calcite core); 1 warm-up + 3 timed runs; mean and 95% CI; wall-clock at ms precision; identical `bench-config.xml` for both jars; same host, same JVM.
**Rationale**: Replicates Task 1 axes for like-for-like comparison; second-scale wall-clock is acceptable for ±10% tolerance.
**Alternatives considered**:
- JMH: overkill for second-scale operations; recorded as future work.
