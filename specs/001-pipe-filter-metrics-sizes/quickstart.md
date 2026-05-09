# Quickstart: Migrating One Check to the Pipe-and-Filter Slice

**Audience**: developer migrating a single existing `*Check.java` (e.g. `MethodLengthCheck`) into the new Pipe-and-Filter shape.
**Pre-reqs**: pipeline core (`checks.pipeline`) and common filters already in place (Phases 1–2 of the implementation plan complete).

## 1. Capture baseline

```powershell
mvn -DskipTests package
java -jar target\checkstyle-*-all.jar -c bench-config.xml violation-sample\SampleAllViolations.java > pre-refactor-output.txt
```

Pin `pre-refactor-output.txt` for diffing after the migration.

## 2. Create the measurement filter

Path: `src/main/java/com/puppycrawl/tools/checkstyle/checks/sizes/pipeline/MethodLengthMeasurementFilter.java`

Move the original `MethodLengthCheck.visitToken` body into `process(Pipe<AstEvent> in, Pipe<AstEvent_unused> out)`-style code that:
1. reads `AstEvent` from `in` (only `VISIT` phase tokens of type `METHOD_DEF`/`CTOR_DEF`/`COMPACT_CTOR_DEF`);
2. computes the length value byte-for-byte as the original;
3. writes `Measurement(subject=ast, lineNo=ast.getLineNo(), colNo=ast.getColumnNo(), value=length, messageKey=MSG_KEY, args={length, max})` to `out`.

The filter does **not** compare against `max` and does **not** call `log`. It is a pure function from input events to a `Measurement` stream.

## 3. Rewrite the driver

Path (unchanged): `src/main/java/com/puppycrawl/tools/checkstyle/checks/sizes/MethodLengthCheck.java`

Driver responsibilities:
- keep class name, package, FQN, public configuration setters (`setMax`, `setCountEmpty`, …), Javadoc, message keys, default tokens;
- field: `private Pipeline<AstEvent, ViolationMessage> pipeline;`
- `init()` / `beginTree(rootAST)`: build the pipeline once
  ```java
  pipeline = PipelineBuilder.<AstEvent>start()
      .add(new TokenFilter(getRequiredTokens()))
      .add(new MethodLengthMeasurementFilter(countEmpty))
      .add(new ThresholdFilter(max))
      .add(new ViolationSink())
      .build();
  ```
- `visitToken(ast)`: `pipeline.submit(new AstEvent(ast, VISIT)); drainAndLog();`
- `leaveToken(ast)`: same with `LEAVE`.
- `drainAndLog()`: `while (pipeline.hasResults()) { ViolationMessage v = pipeline.drain(); log(v.line, v.col, v.messageKey, v.args); }`

Driver must contain **zero** `if (value > max)` lines and **zero** direct `log(...)` calls inside measurement-related methods (only the drain loop logs).

## 4. Verify

```powershell
mvn clean test                                           # full suite must pass
mvn -DskipTests package
java -jar target\checkstyle-*-all.jar -c bench-config.xml violation-sample\SampleAllViolations.java > post-refactor-output.txt
fc pre-refactor-output.txt post-refactor-output.txt      # must show no differences
```

Run ArchUnit + jQAssistant:

```powershell
mvn -Dtest=PipeAndFilterArchitectureTest test
mvn -P jqassistant verify
```

## 5. Definition of done (per check)

- output diff vs baseline = 0 bytes;
- full Maven test suite green;
- ArchUnit rules R1–R10 green;
- jQAssistant constraint queries Q1, Q2, Q4, Q5 return zero rows; Q3 includes the new pipeline.

Repeat for each of the 16 checks in the order specified in `plan.md` Phases 4–7.
