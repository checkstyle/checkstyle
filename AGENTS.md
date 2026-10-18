# AGENTS.md

Guidance for AI coding agents working in this repository (`Checkstyle`, a Java
static-analysis tool). Human-oriented docs live in `docs/` and `.github/CONTRIBUTING.md`.

## Build tools

- `JDK 21` (`<java.version>21</java.version>`), `Maven` via the wrapper: always use `./mvnw`.
- Use a `UTF-8` locale (`LANG=C.UTF-8 LC_ALL=C.UTF-8`). Some test resource file names are
  non-ASCII; under the `POSIX` locale `antrun:run@ant-phase-verify` fails with
  `InvalidPathException: Malformed input`.
- The full build is slow (`clean verify` runs tests, `Checkstyle`, `PMD`, `SpotBugs`,
  `forbiddenapis`, `jacoco`, ...). Prefer the targeted commands below while iterating.

## Common commands

```bash
# Compile only
./mvnw -e --no-transfer-progress clean compile

# Package jars without tests or static analysis (target/checkstyle-*-all.jar is the CLI)
./mvnw -e --no-transfer-progress package -DskipTests -Passembly,no-validations

# Run one test class / method (skip the heavy validation plugins; the no-validations
# profile sets skipTests=true, so it has to be switched back on explicitly)
./mvnw -e --no-transfer-progress test -Pno-validations -DskipTests=false \
  -Dtest=EmptyStatementCheckTest
./mvnw -e --no-transfer-progress test -Pno-validations -DskipTests=false \
  -Dtest='EmptyStatementCheckTest#testEmptyStatements'

# Integration tests (src/it), e.g. Google style / XPath regression
./mvnw -e --no-transfer-progress verify -Pno-validations -DskipTests=false \
  -Dtest=NONE -Dsurefire.failIfNoSpecifiedTests=false -Dit.test=XpathRegressionEmptyStatementTest

# Self-check the codebase with Checkstyle (report: target/cs_errors.xml)
./mvnw -e --no-transfer-progress clean compile antrun:run@ant-phase-verify

# Other static analysis used by CI
./mvnw -e --no-transfer-progress clean test-compile pmd:check
./mvnw -e --no-transfer-progress clean test-compile spotbugs:check
./mvnw -e --no-transfer-progress spotless:check

# Full CI-equivalent build
./mvnw -e --no-transfer-progress clean verify
```

Every CI job is a case in `.ci/validation.sh` (`./.ci/validation.sh <task>`), e.g.
`checkstyle`, `pmd`, `spotbugs`, `spotless`, `verify`, `site`, `openrewrite-recipes`,
`check-missing-pitests`. Look there to reproduce a failing CI step locally.

## Repository layout

- `src/main/java/com/puppycrawl/tools/checkstyle/`
   - `checks/<category>/` — the checks (`*Check.java`), grouped by category
     (`coding`, `whitespace`, `javadoc`, `naming`, `imports`, `indentation`, ...).
   - `api/` — public API (`AbstractCheck`, `DetailAST`, `TokenTypes`, ...).
   - `grammar/` + `src/main/resources/.../grammar/*.g4` — `ANTLR` grammars for Java and `Javadoc`
     (see `docs/GRAMMAR_UPDATES.md`).
   - `filters/`, `xpath/`, `gui/`, `ant/`, `site/` (xdoc generation), `meta/` (module metadata).
- `src/main/resources/.../checks/<category>/messages*.properties` — violation messages, one file
  per language (`en`, `de`, `es`, `fi`, `fr`, `ja`, `pt`, `ru`, `tr`, `zh`). A new message key
  must be added to all of them.
- `src/main/resources/.../meta/checks/<category>/*.xml` — generated module metadata.
- `src/test/java/...` — unit tests, mirroring the main package structure.
- `src/test/resources/.../checks/<category>/<check name in lower case>/Input*.java` — test inputs.
- `src/it/` — integration tests (Google/Sun style configs, `suppressionxpathfilter` `XPath`
  regression tests).
- `src/xdocs-examples/` — runnable examples shown on the website (`Example*.java` +
  `*ExamplesTest.java`).
- `src/site/xdoc/checks/<category>/<check>.xml.template` — check documentation source; the
  matching `.xml` is generated from the template and both are committed.
- `config/` — the project's own quality configs (`checkstyle-checks.xml`, `pmd*.xml`,
  `spotbugs-exclude.xml`, `rewrite.yml`, suppressions, `import-control*.xml`, `java.header`).
- `.ci/` — CI scripts; `.github/workflows/` — `GitHub Actions`.

## Writing tests

- Tests extend `AbstractModuleTestSupport`, override `getPackageLocation()`, and use
  `verifyWithInlineConfigParser(getPath("InputEmptyStatement.java"), expected)`.
- Input files start with a block comment holding the inline config (module name and
  properties). Every expected violation line carries a `// violation '<message>'` comment
  (or `// N violations`, `// violation above`, ...); the parser checks these against the
  `expected` array, so keep them in sync.
- Assertions use `Google Truth`: `assertWithMessage("...").that(x).isEqualTo(y)`.
- Coverage is strict: `jacoco` requires 100% for most code and `pitest` must kill every mutation;
  each main class must be listed in a `pitest-*` profile in `pom.xml`
  (`.ci/validation.sh check-missing-pitests`).
- Meta tests in `src/test/java/.../internal/` (`AllChecksTest`, `XdocsPagesTest`,
  `XdocsExampleFileTest`, `AllTestsTest`, ...) enforce cross-cutting consistency: every check
  needs docs, examples, messages in all languages, metadata, `XPath` regression tests, etc. Run
  the relevant ones after adding or renaming anything public.

## Code conventions

- Every Java file starts with the `LGPL` header from `config/java.header` (year range
  `2001-<current year>`).
- 4-space indentation, max line length 100, `final` on local variables and parameters,
  no wildcard imports; `Javadoc` required on all non-private members of main code.
- New public checks/properties need `@since <next version>` (current dev version is in
  `pom.xml`, e.g. `14.4.0-SNAPSHOT` → `@since 14.4.0`).
- Package dependencies are restricted by `config/import-control.xml` /
  `config/import-control-test.xml`.
- `Checkstyle` checks its own sources with `config/checkstyle-checks.xml`; code must pass it,
  plus `PMD`, `SpotBugs`, `error-prone`, `forbiddenapis` and `OpenRewrite` (`config/rewrite.yml`).

## Commits and PRs

Commit messages are validated (`CommitValidationTest`, `.github/PULL_REQUEST_TEMPLATE.md`):

- One line, ≤ 200 chars, no trailing period/whitespace. No body and no trailers
  (e.g. `Co-Authored-By:`) — `CommitValidationTest` rejects multi-line messages.
- Must match one of:
   - `Issue #<n>: <description>`
   - `Pull #<n>: <description>` (number must equal the PR number)
   - `(minor|config|infra|doc|spelling|dependency): <description>`
- No merge commits in PRs (`git-no-merge-commits`); squash into a single commit per PR.

## OpenRewrite

`config/rewrite.yml` defines the recipe groups (`AllAutoFixes`, `CheckstyleAutoFix`,
`RefasterRules1`, `RefasterRules2`, `StaticAnalysis`); CI runs them via
`.ci/validation.sh openrewrite-*` tasks, e.g.:

```bash
# CheckstyleAutoFix needs the Checkstyle report in target/cs_errors.xml first
./mvnw -e --no-transfer-progress clean compile antrun:run@ant-phase-verify
./mvnw -e --no-transfer-progress rewrite:run -Drewrite.recipeChangeLogLevel=INFO \
  -Drewrite.activeRecipes=org.checkstyle.CheckstyleAutoFix
```

Pitfalls:

- `rewrite:run` forks the lifecycle through `test-compile`, where `jacoco` instruments
  `target/classes` in place. Run `clean` before every rewrite invocation, otherwise the
  `exec` step fails with `NoClassDefFoundError: org/jacoco/agent/rt/...`.
- The plugin's `configLocation` is hard-coded, so `-Drewrite.configLocation` is ignored; to try
  extra recipes, add them to `config/rewrite.yml` temporarily and select them with
  `-Drewrite.activeRecipes=...` (use `rewrite:dryRun` to get `target/rewrite/rewrite.patch`).
- Runs take several minutes; CI uses `MAVEN_OPTS="-Xmx4g -Xms2g"`.
