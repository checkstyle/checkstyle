# Implementation Plan: Pipe-and-Filter Refactoring of Metrics + Sizes Slice

**Branch**: `master` | **Date**: 2026-05-09 | **Spec**: [spec.md](./spec.md)
**Input**: Feature specification from `/specs/001-pipe-filter-metrics-sizes/spec.md`
**Source plan reference**: project-root [plan.md](../../plan.md) (full implementation rationale)

## Summary

Decompose the 16 Checkstyle checks under `checks.metrics` and `checks.sizes` into a Pipe-and-Filter pipeline. Each check becomes: a thin Pipeline Driver (the original `*Check.java`, framework-bound) feeding typed unidirectional pipes through `TokenFilter → <X>MeasurementFilter → ThresholdFilter → ViolationSink`. File-level checks (`FileLengthCheck`, `LineLengthCheck`) substitute `LineSplitterFilter` (and `IgnorePatternFilter` for LineLength) for `TokenFilter`. Coupling checks add `ImportTrackingFilter`. All filters are stateless across invocations, never reference siblings, never call `AbstractCheck.log(..)`. Output is byte-equivalent to baseline.

## Technical Context

**Language/Version**: Java 11+ (existing Checkstyle baseline)
**Primary Dependencies**: Checkstyle `api` (DetailAST, TokenTypes, FullIdent, ScopeUtil, CommonUtil, CheckUtil, AnnotationUtil — allow-listed only); JDK collections (ArrayDeque)
**Storage**: N/A (stateless per-file processing)
**Testing**: JUnit 5 (existing Checkstyle suite), ArchUnit (architecture rules), jQAssistant (constraint queries), Maven Surefire
**Target Platform**: JVM (any host running Checkstyle CLI/Maven plugin/Gradle plugin)
**Project Type**: library/compiler-tooling (Java static analysis tool)
**Performance Goals**: ≤±10% wall-clock delta vs. baseline on 5 benchmark projects (minimal-json, javapoet, gs-core, jgrapht-core, Apache Calcite core)
**Constraints**: Bug-for-bug output equivalence with baseline; existing Maven test suite passes unmodified; no changes outside the slice; no shared mutable state across filters; no callbacks/listeners; no upcalls from filters into framework
**Scale/Scope**: 16 concrete checks + 1 abstract coupling helper; ~35 new classes (5 core + 3 pipes + 4 messages + 5 common filters + 16 measurement filters + 2 coupling filters); 16 driver rewrites in place

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

Constitution file (`.specify/memory/constitution.md`) is the unfilled template (placeholders only). No project-specific principles ratified → no gates to evaluate. Skip; re-evaluate post-design — same outcome expected.

**Status**: PASS (vacuous)

## Project Structure

### Documentation (this feature)

```text
specs/001-pipe-filter-metrics-sizes/
├── plan.md              # This file
├── research.md          # Phase 0 output
├── data-model.md        # Phase 1 output
├── quickstart.md        # Phase 1 output
├── contracts/
│   ├── pipe.md          # Pipe<T> contract
│   ├── filter.md        # Filter<I,O> contract
│   └── messages.md      # AstEvent / FileLine / Measurement / ViolationMessage contracts
├── checklists/
│   └── requirements.md
├── spec.md
└── tasks.md             # Created by /speckit-tasks (not here)
```

### Source Code (repository root)

```text
src/main/java/com/puppycrawl/tools/checkstyle/checks/
├── pipeline/                                      # NEW — core P&F infra (framework-free)
│   ├── Filter.java
│   ├── Pipeline.java
│   ├── PipelineBuilder.java
│   ├── package-info.java
│   ├── pipe/
│   │   ├── Pipe.java
│   │   ├── SingletonPipe.java
│   │   └── QueuePipe.java
│   ├── message/
│   │   ├── AstEvent.java
│   │   ├── FileLine.java
│   │   ├── Measurement.java
│   │   └── ViolationMessage.java
│   └── filter/
│       ├── TokenFilter.java
│       ├── LineSplitterFilter.java
│       ├── IgnorePatternFilter.java
│       ├── ThresholdFilter.java
│       └── ViolationSink.java
├── metrics/
│   ├── pipeline/                                  # NEW — measurement filters
│   │   ├── AbstractCouplingMeasurementFilter.java
│   │   ├── BooleanExpressionMeasurementFilter.java
│   │   ├── ClassDataAbstractionCouplingMeasurementFilter.java
│   │   ├── ClassFanOutComplexityMeasurementFilter.java
│   │   ├── CyclomaticMeasurementFilter.java
│   │   ├── ImportTrackingFilter.java
│   │   ├── JavaNcssMeasurementFilter.java
│   │   ├── NPathMeasurementFilter.java
│   │   └── package-info.java
│   ├── BooleanExpressionComplexityCheck.java      # MODIFIED — driver
│   ├── ClassDataAbstractionCouplingCheck.java     # MODIFIED — driver
│   ├── ClassFanOutComplexityCheck.java            # MODIFIED — driver
│   ├── CyclomaticComplexityCheck.java             # MODIFIED — driver
│   ├── JavaNCSSCheck.java                         # MODIFIED — driver
│   ├── NPathComplexityCheck.java                  # MODIFIED — driver
│   └── AbstractClassCouplingCheck.java            # KEPT — private helper for coupling drivers
└── sizes/
    ├── pipeline/                                  # NEW — measurement filters
    │   ├── AnonInnerLengthMeasurementFilter.java
    │   ├── ExecutableStatementCountMeasurementFilter.java
    │   ├── FileLengthMeasurementFilter.java
    │   ├── LambdaBodyLengthMeasurementFilter.java
    │   ├── LineLengthMeasurementFilter.java
    │   ├── MethodCountMeasurementFilter.java
    │   ├── MethodLengthMeasurementFilter.java
    │   ├── OuterTypeNumberMeasurementFilter.java
    │   ├── ParameterNumberMeasurementFilter.java
    │   ├── RecordComponentNumberMeasurementFilter.java
    │   └── package-info.java
    ├── AnonInnerLengthCheck.java                  # MODIFIED — driver
    ├── ExecutableStatementCountCheck.java         # MODIFIED — driver
    ├── FileLengthCheck.java                       # MODIFIED — driver (file-level)
    ├── LambdaBodyLengthCheck.java                 # MODIFIED — driver
    ├── LineLengthCheck.java                       # MODIFIED — driver (file-level)
    ├── MethodCountCheck.java                      # MODIFIED — driver
    ├── MethodLengthCheck.java                     # MODIFIED — driver (pilot)
    ├── OuterTypeNumberCheck.java                  # MODIFIED — driver
    ├── ParameterNumberCheck.java                  # MODIFIED — driver
    └── RecordComponentNumberCheck.java            # MODIFIED — driver

src/test/java/com/puppycrawl/tools/checkstyle/
├── architecture/
│   └── PipeAndFilterArchitectureTest.java         # NEW — ArchUnit rules R1–R12
├── checks/pipeline/                               # NEW — unit tests for pipes/common filters/messages
└── checks/{metrics,sizes}/pipeline/               # NEW — per-check measurement filter tests

jqassistant/rules/
└── pipe-and-filter.xml                            # NEW — Q1–Q5 constraint queries

structurizr/
└── workspace.dsl                                  # MODIFIED — L2/L3/L4 diagrams updated
```

**Structure Decision**: Single Maven project (existing Checkstyle layout). New code lives entirely under `checks.pipeline` (framework-free core), `checks.metrics.pipeline`, `checks.sizes.pipeline` (measurement filters). Driver rewrites stay in their original FQN to preserve XML compatibility. Files outside the slice (Checker, TreeWalker, JavaParser, ConfigurationLoader, PackageObjectFactory, messages.properties, other checks/) are not modified.

## Phases

### Phase 0 — Research → `research.md`

Resolve all decisions that affect the public surface of pipes/filters/messages. No NEEDS CLARIFICATION remaining in the spec; research consolidates choices already implied.

### Phase 1 — Design & Contracts

Generate `data-model.md` (entity catalog), `contracts/` (Pipe, Filter, Message contracts as text — Java is the implementation, the contracts here are the prose-level invariants enforced by ArchUnit/jQAssistant), `quickstart.md` (how to migrate one check end-to-end). Update `CLAUDE.md` plan reference between SPECKIT markers.

### Phase 2 — Tasks (handled by `/speckit-tasks`, not this command)

## Complexity Tracking

| Violation | Why Needed | Simpler Alternative Rejected Because |
|-----------|------------|-------------------------------------|
| Two `Pipe<T>` implementations (`SingletonPipe`, `QueuePipe`) instead of one | Singleton avoids allocating a deque for the 1-message-at-a-time AST pipelines (14 of 16 checks). Queue is required for splitter (1→N) and sink (file-may-have-many violations). | Single `QueuePipe` everywhere wastes allocation per AST event; profiling concerns and the assignment's ±10% tolerance argue for the cheap variant. |
| `AbstractClassCouplingCheck` retained as private helper | Assignment forbids "rewriting check logic"; the type-resolution helpers count as logic and are used by both coupling checks. | Inlining duplicates the helper into both coupling filters (rewrite). Pulling into a shared filter base creates filter-to-filter inheritance (forbidden by R9 spirit). |
| Tab-width passed via driver constructor instead of pipe message | Tab-width is configuration, not data; threading it through every `FileLine` would pollute the message type for one check's benefit. | Filter upcall to framework property is forbidden (R5/R6); message-channel propagation bloats the type. |
