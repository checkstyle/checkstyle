# AGENTS.md

Guidance for AI coding agents working in this repository (`Checkstyle`, a Java
static-analysis tool). Human-oriented docs live in `docs/` and `.github/CONTRIBUTING.md`.

## Build

- `JDK 21`, `Maven` via the wrapper: always use `./mvnw`.
- Use a `UTF-8` locale (`LANG=C.UTF-8 LC_ALL=C.UTF-8`). Some test resource file names are
  non-ASCII; under the `POSIX` locale `antrun:run@ant-phase-verify` can fail with
  `InvalidPathException: Malformed input`.
- The full build is slow; prefer the targeted commands below while iterating.
- Start with `clean` once `target/` holds output of an earlier build: `jacoco` instruments
  `target/classes` in place during `test-compile`, so a later single-test run fails with
  `NoClassDefFoundError: org/jacoco/agent/rt/...`, and `spotless:check` reports thousands of
  false violations from compiled test classes.

## Common commands

```bash
# Compile only
./mvnw -e --no-transfer-progress clean compile

# Package jars without tests or static analysis (target/checkstyle-*-all.jar is the CLI)
./mvnw -e --no-transfer-progress clean package -DskipTests -Passembly,no-validations

# Run one test class / method. The no-validations profile skips the heavy validation
# plugins but also sets skipTests=true and skipITs=true, so switch those back on;
# otherwise Maven prints "Tests are skipped" and still ends in BUILD SUCCESS.
./mvnw -e --no-transfer-progress clean test -Pno-validations -DskipTests=false \
  -Dtest=EmptyStatementCheckTest
./mvnw -e --no-transfer-progress clean test -Pno-validations -DskipTests=false \
  -Dtest='EmptyStatementCheckTest#testEmptyStatements'

# Integration tests (src/it), e.g. Google style / XPath regression
./mvnw -e --no-transfer-progress clean verify -Pno-validations -DskipTests=false \
  -DskipITs=false -Dtest=NONE -Dsurefire.failIfNoSpecifiedTests=false \
  -Dit.test=XpathRegressionEmptyStatementTest

# Self-check the codebase with Checkstyle (report: target/cs_errors.xml)
./mvnw -e --no-transfer-progress clean compile antrun:run@ant-phase-verify

# Other static analysis used by CI
./mvnw -e --no-transfer-progress clean test-compile pmd:check
./mvnw -e --no-transfer-progress clean test-compile spotbugs:check
./mvnw -e --no-transfer-progress clean spotless:check

# Full Maven build (Error Prone, PIT and OpenRewrite run as separate CI jobs)
./mvnw -e --no-transfer-progress clean verify
```

Most CI jobs are a case in `.ci/validation.sh` (`./.ci/validation.sh <task>`), e.g.
`checkstyle`, `pmd`, `spotbugs`, `spotless`, `verify`, `check-missing-pitests`; Error Prone
and PIT have their own scripts in `.ci/`, shellcheck runs in `.github/workflows/shellcheck.yml`.
Look there to reproduce a failing CI step.

## Generated files

Any build that reaches `process-classes` (`test-compile`, `test`, `package`, ...) regenerates
the check documentation (`src/site/xdoc/**/*.xml`, from the `.xml.template` files) and the
module metadata (`src/main/resources/.../meta/**/*.xml`). Commit these regenerated files
together with the change that caused them.

## Changing checks

- Each `messages*.properties` file exists in 10 languages (`en`, `de`, `es`, `fi`, `fr`, `ja`,
  `pt`, `ru`, `tr`, `zh`). A new message key must be added to all of them.
- New public checks/properties need `@since` with the version from `pom.xml` without
  `-SNAPSHOT` (`14.4.0-SNAPSHOT` → `@since 14.4.0`); if the patch digit is not zero, use the
  next minor version (`14.4.1-SNAPSHOT` → `@since 14.5.0`).
- Meta tests in `src/test/java/.../internal/` (`AllChecksTest`, `XdocsPagesTest`,
  `XdocsExampleFileTest`, `AllTestsTest`, ...) enforce cross-cutting consistency: every check
  needs docs, examples, messages in all languages, metadata, `XPath` regression tests, etc. Run
  the relevant ones after adding or renaming anything public.
- PIT: a change must not introduce new surviving mutations; the only accepted survivors are
  those listed in `config/pitest-suppressions/`. Each main class must be covered by a
  `pitest-*` profile in `pom.xml` (`.ci/validation.sh check-missing-pitests`).
- Code style is enforced by `config/checkstyle-checks.xml`; run the self-check above.

## Writing tests

See `docs/TestingTechniques.md` and `docs/specifying-violations.md` (inline config and
`// violation` comments in `Input*.java` files).

## Commits and PRs

Commit messages are validated (`CommitValidationTest`, `.github/PULL_REQUEST_TEMPLATE.md`):

- One line, ≤ 200 chars, no trailing period/whitespace. No message body and no trailers
  (e.g. `Co-Authored-By:`) — `CommitValidationTest` rejects multi-line messages.
- Must match one of:
   - `Issue #<n>: <description>`
   - `Pull #<n>: <description>` (number must equal the PR number)
   - `(minor|config|infra|doc|spelling|dependency|supplemental): <description>`
- A fix for a defect must use `Issue #<n>:` so that it appears in the release notes; never use
  `minor:` for defects.
- Every defect or behavior change needs an issue (`.github/ISSUE_TEMPLATE/`) with real CLI
  output from the latest release; a maintainer must label it `approved` before the PR is merged.
- One commit per PR, no merge commits (`git-check-single-commit`, `git-no-merge-commits`).
- Run `mvnw clean verify` before PR creation, it must pass without any errors.

## OpenRewrite

CI runs the recipes from `config/rewrite.yml` via `.ci/validation.sh` tasks
`openrewrite-checkstyle-auto-fix`, `openrewrite-refaster-rules-1`,
`openrewrite-refaster-rules-2` and `openrewrite-static-analysis`. `rewrite:run` forks the
lifecycle through `test-compile`, so run `clean` before every invocation (see the `jacoco` note
above).
