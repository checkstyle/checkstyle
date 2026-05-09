# Feature Specification: Pipe-and-Filter Refactoring of Metrics + Sizes Slice

**Feature Branch**: `master`
**Created**: 2026-05-09
**Status**: Draft
**Input**: User description: "Refactor the Checkstyle metrics + sizes slice (16 concrete checks under `com.puppycrawl.tools.checkstyle.checks.metrics` and `com.puppycrawl.tools.checkstyle.checks.sizes`) into a Pipe-and-Filter architecture. Each check decomposes into independent filters connected by typed unidirectional pipes carrying immutable value messages. The outer `*Check.java` becomes a thin Pipeline Driver that keeps Checkstyle plug-compatible. No shared mutable state, no listeners, no callbacks across filters. The slice must remain bug-for-bug equivalent to the original (zero output diff against baseline) and the existing Maven test suite must pass unmodified."

## User Scenarios & Testing *(mandatory)*

### User Story 1 - Plug-Compatible Refactored Slice (Priority: P1)

Existing Checkstyle users keep their current XML configurations and continue running the 16 metrics/sizes checks. After the refactor, the same configuration files load, the same violations fire, and the same message keys appear in the same order.

**Why this priority**: Output equivalence is the assignment's hard constraint. Without it the refactor is rejected regardless of architectural quality.

**Independent Test**: Run the original `checkstyle.jar` and the refactored jar against `violation-sample/SampleAllViolations.java` with identical config; `diff` of the two outputs must be empty. Verifiable without knowing internal architecture.

**Acceptance Scenarios**:

1. **Given** an unchanged `google_checks.xml`, **When** the refactored jar runs against any Java project, **Then** the violation list matches the original jar's violation list byte-for-byte.
2. **Given** the existing Maven test suite (`mvn clean test`), **When** executed against the refactored slice, **Then** 0 failures, 0 errors.
3. **Given** a user XML referencing `MethodLengthCheck` by FQN with `setMax`, **When** loaded by `ConfigurationLoader`, **Then** the driver instantiates and accepts the property exactly as before.

---

### User Story 2 - Architectural Conformance (Priority: P1)

Reviewers (graders, ArchUnit, jQAssistant) can verify the slice obeys Pipe-and-Filter rules: no shared mutable state across filters, no callbacks, only typed pipes, no filter calls into Checkstyle's `api` package, only the driver bridges the framework.

**Why this priority**: Architectural conformance is the second hard assignment requirement; failure to enforce structurally invalidates the submission.

**Independent Test**: Run the ArchUnit rule suite (`PipeAndFilterArchitectureTest`) and the jQAssistant constraint queries (`pipe-and-filter.xml`); both report zero violations.

**Acceptance Scenarios**:

1. **Given** any class under `..checks.pipeline..`, **When** ArchUnit checks dependencies, **Then** it does not extend `AbstractCheck` or `AbstractFileSetCheck`.
2. **Given** any measurement filter, **When** scanned for `INVOKES` edges to `AbstractCheck.log(..)`, **Then** zero edges found.
3. **Given** the filter dependency graph, **When** queried for cycles, **Then** zero cycles.
4. **Given** any filter class, **When** inspected, **Then** it has no field or constructor parameter typed as another concrete `Filter` implementation (only `Pipe<?>`).

---

### User Story 3 - Performance Within Tolerance (Priority: P2)

End-users running Checkstyle in CI experience no meaningful slowdown after the refactor.

**Why this priority**: Required for Part B of the assignment but does not block correctness.

**Independent Test**: Benchmark both jars on five projects (`minimal-json`, `javapoet`, `gs-core`, `jgrapht-core`, Apache Calcite core) with `bench-config.xml`; mean wall-clock delta within ±10% per project across 1 warm-up + 3 timed runs.

**Acceptance Scenarios**:

1. **Given** identical `bench-config.xml` and identical hardware, **When** both jars analyze each benchmark project, **Then** the refactored jar's mean wall-clock time is within ±10% of the original.
2. **Given** the benchmark results, **When** plotted on the Task 1 axes, **Then** the curve shape matches (no super-linear regression).

---

### User Story 4 - Independent Filter Testability (Priority: P3)

Future maintainers can unit-test each measurement filter without booting Checkstyle, and can swap or extend stages by editing the driver only.

**Why this priority**: Quality-of-life benefit of the chosen architecture; not graded directly but supports lessons-learned section of the report.

**Independent Test**: Each `<X>MeasurementFilterTest` runs with hand-built `AstEvent` streams, asserts emitted `Measurement` stream, and depends on no Checkstyle execution context.

**Acceptance Scenarios**:

1. **Given** a hand-built `AstEvent` sequence, **When** fed to `MethodLengthMeasurementFilter`, **Then** it emits the expected `Measurement` without instantiating `TreeWalker`.
2. **Given** a new threshold value, **When** the driver constructs the pipeline with a different `ThresholdFilter`, **Then** no measurement filter changes.

---

### Edge Cases

- File-level checks (`FileLengthCheck`, `LineLengthCheck`) receive a `FileText`, not AST events; the splitter filter must yield 1-based line numbers identical to the original.
- `LineLengthCheck` ignore-pattern (default `^(package|import) .*`) must drop matching lines before measurement, not after.
- Tab-width expansion in `LineLengthCheck` must occur inside the measurement filter using the `tabWidth` value passed via the driver constructor (no upcall to framework).
- Coupling checks (`ClassDataAbstractionCoupling`, `ClassFanOutComplexity`) need import/package context tracked across token visits; `ImportTrackingFilter` must observe `IMPORT`/`PACKAGE_DEF` events without sharing state with downstream filters.
- One input AST event can yield zero violations (typical), one violation, or — across a file — many; `QueuePipe<ViolationMessage>` at the sink must preserve order.
- `FileLengthCheck` only needs the line count, not every line; the measurement filter drains the input pipe and emits exactly one `Measurement` at end-of-input.
- Pipeline must drain immediately after each `submit` so original log ordering is preserved.

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: System MUST decompose every check in the metrics + sizes slice into independent filters connected by typed unidirectional pipes.
- **FR-002**: System MUST carry only immutable value messages between filters: `AstEvent`, `FileLine`, `Measurement`, `ViolationMessage`.
- **FR-003**: System MUST forbid filters from holding references to other filters; constructor parameters and fields may only be `Pipe<?>` instances or scalar configuration.
- **FR-004**: System MUST keep the original `*Check.java` class name, FQN, package, configuration setters, message keys, default tokens, and Javadoc unchanged for all 16 checks.
- **FR-005**: System MUST forbid measurement filters from calling `AbstractCheck.log(..)` or any Checkstyle execution-infrastructure method.
- **FR-006**: System MUST limit driver responsibilities to: building the pipeline once, translating framework callbacks (`visitToken`, `leaveToken`, `beginTree`, `finishTree`, `processFiltered`) into messages, draining the sink, and forwarding `ViolationMessage` to `log(..)`.
- **FR-007**: System MUST preserve violation output identical to the pre-refactor baseline (`pre-refactor-output.txt`) on `violation-sample/SampleAllViolations.java`.
- **FR-008**: System MUST keep the existing Maven test suite passing without test modification.
- **FR-009**: System MUST provide ArchUnit rules R1–R10 enforcing all P&F invariants in `PipeAndFilterArchitectureTest`.
- **FR-010**: System MUST provide jQAssistant queries Q1–Q5 in `jqassistant/rules/pipe-and-filter.xml`; constraint queries return zero rows.
- **FR-011**: System MUST update C4 diagrams (L2, L3, L4 — exemplar `MethodLengthCheck`) in `structurizr/workspace.dsl` to reflect the new architecture.
- **FR-012**: System MUST provide a regression test (`RegressionDiffTest`) that re-runs the jar against the sample input and asserts byte-equal output against the pinned baseline.
- **FR-013**: System MUST migrate checks in the order: pilot `MethodLengthCheck`, then remaining size AST checks, then file-level checks, then complexity checks (`BooleanExpressionComplexity`, `CyclomaticComplexity`, `NPathComplexity`, `JavaNCSS`), then coupling checks last.
- **FR-014**: System MUST keep `AbstractClassCouplingCheck` only as a private helper for the two coupling drivers; its measurement bodies move into the coupling measurement filter.
- **FR-015**: System MUST not modify any file outside the slice (in particular: `Checker`, `TreeWalker`, `JavaParser`, `ConfigurationLoader`, `PackageObjectFactory`, `messages.properties`, other `checks/*` packages, XML configurations). Enforcement: ArchUnit rule R11 (`PipeAndFilterArchitectureTest`) asserts the modified-class allow-list.
- **FR-016**: System MUST split `LineLengthCheck` responsibilities: tab-width expansion inside `LineLengthMeasurementFilter`, ignore-pattern guard inside `IgnorePatternFilter`.
- **FR-017**: System MUST validate type-compatibility between adjacent filters at pipeline build time via `PipelineBuilder` (compile-time generics + runtime assertion).
- **FR-018**: System MUST provide both `SingletonPipe<T>` (single-message slot) and `QueuePipe<T>` (FIFO buffer) implementations of `Pipe<T>`.

### Key Entities

- **Pipe<T>**: typed unidirectional channel between two filters; supports `write`, `read`, `hasNext`, `close`. Two implementations: `SingletonPipe`, `QueuePipe`.
- **Filter<I,O>**: contract for a processing stage; `process(Pipe<I> in, Pipe<O> out)`.
- **Pipeline<HEAD,TAIL>**: composed, immutable chain of filter stages built by `PipelineBuilder`.
- **AstEvent**: immutable message carrying `DetailAST node` + `Phase phase` (BEGIN_TREE, VISIT, LEAVE, FINISH_TREE).
- **FileLine**: immutable message carrying `int lineNo` + `String text`.
- **Measurement**: immutable message carrying subject AST, line/col, numeric value, and context for downstream message construction.
- **ViolationMessage**: immutable message carrying line, col, message key, args.
- **TokenFilter / LineSplitterFilter / IgnorePatternFilter / ThresholdFilter / ViolationSink**: common reusable filters.
- **Per-check measurement filters (16)**: one per check; encapsulates the original measurement algorithm.
- **ImportTrackingFilter / AbstractCouplingMeasurementFilter**: coupling-specific filters.
- **Pipeline Driver (16)**: outer `*Check.java`; bridges Checkstyle framework to pipeline; no measurement, no threshold logic.

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: Output diff between original jar and refactored jar on `SampleAllViolations.java` is zero bytes.
- **SC-002**: `mvn clean test` reports 0 failures and 0 errors against the unmodified test suite.
- **SC-003**: All 10 ArchUnit rules (R1–R10) pass; all 5 jQAssistant constraint queries (Q1–Q5) return 0 rows.
- **SC-004**: Mean wall-clock benchmark delta within ±10% of original on each of the five benchmark projects across 1 warm-up + 3 timed runs.
- **SC-005**: Each of the 16 checks fires at least once on the bundled sample input file (per-check fire test).
- **SC-006**: Zero `if (value > max)` or direct `log(...)` calls remain inside measurement methods of any of the 16 outer drivers. Enforcement: ArchUnit rule R12 scans driver bytecode and fails the build on any `log(..)` call outside `drainAndLog` or any `>` comparison against the `max` field outside the same method.
- **SC-007**: Filter dependency graph contains zero cycles (jQAssistant Q4).
- **SC-008**: All filter classes have zero fields or constructor parameters typed as another concrete `Filter` implementation.

## Assumptions

- Checkstyle remains single-threaded per file; pipelines are synchronous in-thread; no concurrency primitives required.
- The benchmark host, JVM version, and OS are held constant across original-vs-refactored runs; wall-clock measurement is acceptable in lieu of JMH at second-scale.
- The ArchUnit allow-list of permitted Checkstyle utility/AST types (`DetailAST`, `TokenTypes`, `FullIdent`, `ScopeUtil`, `CommonUtil`, `CheckUtil`, `AnnotationUtil`) is fixed by code review; additions require PR approval.
- "Check logic must not be rewritten" is interpreted at the algorithm level: identical computation is moved byte-for-byte into the matching measurement filter; only call shape (emit `Measurement` instead of `log(..)`) changes.
- Tab-width is a per-driver constructor argument; the framework property setter remains on the driver and propagates the value into the filter at pipeline build time.
- `messages.properties` is unchanged; message keys are reused verbatim from the original checks.
- The five benchmark projects from Task 1 Part B remain accessible; baseline jar is preserved at `baseline/checkstyle-original.jar`.
- Output ordering is preserved by draining the sink immediately after each `submit` from the driver.
