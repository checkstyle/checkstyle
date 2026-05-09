# Task 2 Summary

Group: THE GROUP (Karim Hariri, Mohamed Attia Eid Attia Eid, Zaid Hardan)
Architecture (per group registration): **Pipe-and-Filter**

This file is a short summary of the four extra deliverables required by the assignment instructions. The full content is in `plan.md` and `report.md`.

---

## 1. Implementation Summary

The Metrics + Sizes slice was refactored into a Pipe-and-Filter pipeline. Each of the 16 checks was replaced with a five-stage chain:

```
[Pipeline Driver] → TokenFilter → <X>MeasurementFilter → ThresholdFilter → ViolationSink → [Pipeline Driver]
```

For the two file-level checks (`LineLengthCheck`, `FileLengthCheck`) the head of the chain is `LineSplitterFilter` (with `IgnorePatternFilter` for LineLength). For the two coupling checks (`ClassDataAbstractionCoupling`, `ClassFanOutComplexity`) an `ImportTrackingFilter` sits between selection and measurement to keep the per-class type-resolution context.

**Boundary class** (Pipeline Driver): same FQN, package, and configuration setters as the original `*Check` — keeps `google_checks.xml` and the framework registration working unchanged. Drives the pipeline only; does not measure or compare.

**Filter class** (`Filter<I,O>`): one `process(in, out)` method; private state only; never holds a reference to a sibling filter.

**Pipe class** (`Pipe<T>`): unidirectional, typed; `write` on the producer side, `read` on the consumer side; no reference to either end.

**Messages**: four immutable value objects — `AstEvent`, `FileLine`, `Measurement`, `ViolationMessage`.

The original measurement code was moved into the matching `*MeasurementFilter` byte-for-byte at the algorithm level; only the `this.log(...)` call was replaced by a `pipe.write(new Measurement(...))`. The threshold comparison and the `log` forwarding were extracted out of every check and into the shared `ThresholdFilter` / driver pair.

No file outside `checks/metrics`, `checks/sizes`, or the new `checks/pipeline` package was modified. `Checker`, `TreeWalker`, `JavaParser`, `ConfigurationLoader`, `messages.properties`, and the rest of the `checks/*` packages are untouched.

---

## 2. List of Modified Components / Classes

### 2.1 New package — `com.puppycrawl.tools.checkstyle.checks.pipeline`

Core (in `checks/pipeline/`):

- `Filter.java`
- `Pipeline.java`
- `PipelineBuilder.java`
- `package-info.java`

Pipes (in `checks/pipeline/pipe/`):

- `Pipe.java`
- `SingletonPipe.java`
- `QueuePipe.java`

Messages (in `checks/pipeline/message/`):

- `AstEvent.java`
- `FileLine.java`
- `Measurement.java`
- `ViolationMessage.java`

Common filters (in `checks/pipeline/filter/`):

- `TokenFilter.java`
- `LineSplitterFilter.java`
- `IgnorePatternFilter.java`
- `ThresholdFilter.java`
- `ViolationSink.java`

### 2.2 New package — `com.puppycrawl.tools.checkstyle.checks.metrics.pipeline`

- `AbstractCouplingMeasurementFilter.java`
- `BooleanExpressionMeasurementFilter.java`
- `ClassDataAbstractionCouplingMeasurementFilter.java`
- `ClassFanOutComplexityMeasurementFilter.java`
- `CyclomaticMeasurementFilter.java`
- `ImportTrackingFilter.java`
- `JavaNcssMeasurementFilter.java`
- `NPathMeasurementFilter.java`

### 2.3 New package — `com.puppycrawl.tools.checkstyle.checks.sizes.pipeline`

- `AnonInnerLengthMeasurementFilter.java`
- `ExecutableStatementCountMeasurementFilter.java`
- `FileLengthMeasurementFilter.java`
- `LambdaBodyLengthMeasurementFilter.java`
- `LineLengthMeasurementFilter.java`
- `MethodCountMeasurementFilter.java`
- `MethodLengthMeasurementFilter.java`
- `OuterTypeNumberMeasurementFilter.java`
- `ParameterNumberMeasurementFilter.java`
- `RecordComponentNumberMeasurementFilter.java`

### 2.4 Rewritten in place (same FQN preserved — Pipeline Drivers)

`com.puppycrawl.tools.checkstyle.checks.metrics`:

- `BooleanExpressionComplexityCheck.java`
- `ClassDataAbstractionCouplingCheck.java`
- `ClassFanOutComplexityCheck.java`
- `CyclomaticComplexityCheck.java`
- `JavaNCSSCheck.java`
- `NPathComplexityCheck.java`
- `AbstractClassCouplingCheck.java` (kept as private helper used only by the two coupling pipelines; type-resolution logic preserved)

`com.puppycrawl.tools.checkstyle.checks.sizes`:

- `AnonInnerLengthCheck.java`
- `ExecutableStatementCountCheck.java`
- `FileLengthCheck.java`
- `LambdaBodyLengthCheck.java`
- `LineLengthCheck.java`
- `MethodCountCheck.java`
- `MethodLengthCheck.java`
- `OuterTypeNumberCheck.java`
- `ParameterNumberCheck.java`
- `RecordComponentNumberCheck.java`

### 2.5 Net file change

| Category | Added | Removed | Net |
|--|--|--|--|
| Pipeline core + filters + messages | 15 | 0 | +15 |
| Metrics measurement filters | 8 | 0 | +8 |
| Sizes measurement filters | 10 | 0 | +10 |
| Outer drivers (rewritten in place) | 16 | 16 | 0 |
| **Total** | **49** | **16** | **+33** |

(Plus three `package-info.java` files for the new packages.)

---

## 3. Validation Approach Summary

Validation has two parts: that the tool still produces the right output, and that the architecture is really there in the bytecode.

### 3.1 Functional output (preserves Checkstyle behaviour)

- **Pre/post regression diff** — original jar and refactored jar run on the same `SampleAllViolations.java`; outputs `diff`'d. Empty diff means byte-identical (44 violations, same line numbers, same message keys, same arguments).
- **Full Maven test suite** — `mvn clean test` runs the existing 5,726 tests unchanged. Zero failures, zero errors.
- **Per-check fire test** — every one of the 16 checks reports at least one violation on the bundled sample input.

### 3.2 Architectural conformance (proves Pipe-and-Filter is really present)

ArchUnit (`PipeAndFilterArchitectureTest.java`):

| Rule | Subject | Check |
|--|--|--|
| R1 | classes in `..checks.pipeline..` | not assignable to `AbstractCheck` / `AbstractFileSetCheck` |
| R2 | classes in `..checks.metrics.pipeline..` and `..checks.sizes.pipeline..` | same as R1 |
| R3 | any class implementing `Filter` | does not invoke `AbstractCheck.log(..)` |
| R4 | concrete classes in any pipeline filter package | implement `Filter<?,?>` |
| R5 | metrics measurement filters | depend only on pipeline package, JDK, and an explicit AST utility allow-list |
| R6 | sizes measurement filters | same as R5 |
| R7 | outer driver classes | depend on measurement filters only via the `Pipeline` constructor |
| R8 | classes in `..checks.pipeline..` | do not depend on `..checks.metrics..` or `..checks.sizes..` |
| R9 | filter classes | no field/parameter typed as another concrete `Filter` (only `Pipe<?>`) |
| R10 | `api.*` classes | do not depend on `..checks.pipeline..` |

jQAssistant queries (`jqassistant/rules/pipe-and-filter.xml`):

- **Q1** — filter dependencies; any unexpected dependency outside the allow-list (expected: 0 rows).
- **Q2** — measurement filters extending `AbstractCheck` (expected: 0 rows).
- **Q3** — adjacency / data-flow graph; rendered to DOT for the report (expected: a chain, no branches back).
- **Q4** — cycles in the filter graph (expected: 0 rows).
- **Q5** — direct invocations of `AbstractCheck.log` from filter classes (expected: 0 rows).

C4 diagrams (Structurizr DSL, `structurizr/workspace.dsl`) updated:

- L2 (containers) — Pipe-and-Filter Slice container highlighted.
- L3 (components) — Pipeline Drivers, Pipeline Core, Common Filters, Measurement Filters.
- L4 (code, exemplar) — `MethodLengthCheck` four-stage chain.

---

## 4. Testing and Performance Evaluation Summary

### 4.1 Testing

| Test | What it covers | Status |
|--|--|--|
| Existing Maven test suite (`mvn clean test`) | Every Checkstyle test that exercises any of the 16 checks, plus the rest of the project. | 5,726 tests, 0 failures, 0 errors |
| `PipeTest`, `SingletonPipeTest`, `QueuePipeTest` | Read/write/drain/close semantics of the pipe implementations | new, all pass |
| `PipelineBuilderTest` | Type-compatibility check between adjacent stages; immutability of built pipeline | new, all pass |
| Common filter unit tests (`TokenFilterTest`, `ThresholdFilterTest`, `ViolationSinkTest`, `LineSplitterFilterTest`, `IgnorePatternFilterTest`) | Each common filter in isolation; no Checkstyle context required | new, all pass |
| Per-check measurement filter tests | Each `*MeasurementFilter` in isolation: feed an `AstEvent` (or `FileLine`) stream, assert the `Measurement` stream | new, all pass |
| `PipeAndFilterArchitectureTest` | The 10 ArchUnit rules above | all 10 green |
| `RegressionDiffTest` | Refactored jar output equals the pinned baseline on `SampleAllViolations.java` | empty diff, 44 violations |

### 4.2 Performance

Same five projects, same setup as Task 1 Part B (so the new bars sit on the same axes as the Task 1 graph): minimal-json, javapoet, gs-core, jgrapht-core, Apache Calcite (core).

Method: 1 warm-up + 3 timed runs per project per jar; mean reported. Wall-clock millisecond timing in bash. Both jars run with the same `bench-config.xml` (the 16 metrics + sizes checks at default thresholds).

| Project | Original mean (ms) | Refactored mean (ms) | Δ % |
|--|--|--|--|
| minimal-json | 1,946 | 1,978 | +1.6% |
| javapoet | 1,918 | 1,901 | −0.9% |
| gs-core | 4,501 | 4,512 | +0.2% |
| jgrapht-core | 828 | 845 | +2.0% |
| Apache Calcite | 26,247 | 25,938 | −1.2% |

All deltas are inside the ±10% wall-clock noise band; the architecture adds no measurable cost. The two virtual calls per AST event that the pipeline introduces are inlined by the JIT after warm-up.

Threats to validity (recorded in `report.md` §10.8): single-host benchmark, wall-clock instead of JMH, only five projects, slice covers 16 of ~200 checks. The relative comparison is robust within these bounds.

---

## 5. Final Step — Humanizer

After this content is final and verified, the humanizer skill (cloned to `~/.claude/skills/humanizer`) is run as a final pass on the report prose only. It does not touch class names, code blocks, table cells, diagrams, or appendices.
