# New Language Feature Check Integration Process

The ability to parse new language features must be implemented and
merged before following this document.

Updating check modules and creating new checks to support new language features aligns Checkstyle
with current best practices in the Java community and contributes to
the project's ongoing evolution. This document outlines the procedures
for integrating new language features into Checkstyle,
by updating existing checks and creating new ones.

---

## 1. Analyze Current Check Support for This Java Version

### 1.1 Review Java Enhancement Proposals (JEPS)

Review Java Enhancement Proposals (JEPS) related to the new feature.
JEPS provide detailed information about the goals
and motivations behind new language features.
This information can help us understand the feature better and figure out
which checks are most likely to be impacted or what new checks might be needed.
Key concepts to consider:

- **Problem identification**: Understand what problem the new feature solves to build
  insights about which checks are more likely to be impacted.

- **Impact on Readability**: Assess whether the new feature enhances code readability.
  Not all new features will necessarily improve readability, but if this one does,
  Checkstyle can promote its use to enhance clarity and reduce verbosity.

**Examples**:

- **Check updates**:  For unnamed variables (`_`), the JEP guides us to recognize the new role of
  the underscore for unnamed variables and avoid flagging the non-use of such variables.
  Consequently, we updated `UnusedLocalVariableCheck` to ensure it does not
  incorrectly violate unnamed variables.

- **New checks**: Pattern Matching for `instanceof`, where the newer pattern matching feature
  provides a more concise and type-safe alternative to traditional casting.
  Ideally, we would consider creating a check
  to suggest the use of this new feature if we see the typical `if X instanceof Y`
  followed by a typecasting pattern.

### 1.2 Consider Similar and Related Tokens

Identify existing tokens that are similar to the new language feature tokens.
This comparison helps to determine which existing checks may be relevant
and require updates to support the new token. Additionally,
we should consider the impact that a new child token may have
on existing checks. By examining the acceptable tokens of existing checks and
comparing them with the new tokens, you can determine which checks are likely to be impacted.

**Examples**:

- **Check updates**: When the `RECORD_DEF` token was introduced,
 it was reasonable to look at all checks that had `CLASS_DEF` in their acceptable tokens.

- **Check updates**: When the `TEXT_BLOCK_CONTENT` token was introduced,
  it was reasonable to look at all checks that had `STRING_LITERAL` in their acceptable tokens.

- **Check updates**: When switch expressions were introduced, it was reasonable to look at all
  checks that deals with expressions because `LITERAL_SWITCH` may now fall under `EXPR` token.
  This requires analyzing the impact of this new child token on existing checks,
  such as `InnerAssignmentCheck`

- **New checks**: When the `ENUM_DEF` token was introduced,
  we examined checks that used `ARRAY_INIT` tokens and found that checks
  like `NoArrayTrailingComma` were similar. However, it was determined that a new check,
  `NoEnumTrailingComma`, was needed to handle enums.
  We recommend creating separate checks because they handle different tokens
  with distinct specifics, making configuration easier for users and allowing for
  more tailored behavior.

### 1.3 Frequently Impacted Checks

Certain checks are most likely to be impacted by new language features.
We should analyze the impact on the following frequently affected checks:

- [IllegalToken](https://checkstyle.org/checks/coding/illegaltoken.html): This check violates the
  usage of specified tokens. Since new language features often introduce new tokens,
  it's important to review this check.

- [IllegalTokenText](https://checkstyle.org/checks/coding/illegaltokentext.html):
  This check identifies and restricts customizable text patterns in the code.
  New language features can provide programmers new ways to add custom text to
  the code which would need to be scanned.

- [Indentation](https://checkstyle.org/checks/misc/indentation.html#Indentation):
  As new language features are introduced, it's important to ensure that they
  follow proper indentation rules and that this check works as expected when executed on them.

- [Whitespace](https://checkstyle.org/checks/whitespace/index.html):
  This check impacts almost all language constructs by ensuring proper
  use of whitespace before, after, or around them. As new language features
  introduce new constructs, it's important to review and update this check.

### 1.4 Discover Similar Checks

Look for existing checks that address concepts
similar to those introduced by the new language feature.
These checks serve as references for designing and implementing new checks.

**Examples**:

- **Identifiers and Naming Conventions**: If the new feature introduces
  new tokens involving identifiers, explore existing checks related to naming conventions.
  We can create a new similar check to enforce naming conventions for the new feature.

- **Sizes**: If we have a check that enforces a limit on the number of parameters
  a method can have, we created a similar check for the number of record components
  to ensure that records do not have an excessive number of components.

### 1.5 Review Other Static Analysis Tools

Examining how other static analysis tools handle new language features
can provide valuable insights into best practices and potential pitfalls.
A list to a few of the popular ones:

- [IntelliJ Inspections](https://www.jetbrains.com/help/idea/code-inspection.html)
- [PMD](https://pmd.github.io/)
- [SonarQube](https://www.sonarqube.org/)

**Examples**:

- **Check updates**: IntelliJ IDEA introduced an inspection rule for Java 21 or higher to detect
  redundant `strictfp` modifiers. This resulted in the analysis of `RedundantModifierCheck`
  to verify if we needed to update it.

- **New check**: Sonar introduced a [new rule](https://rules.sonarsource.com/java/tag/java21/RSPEC-6916/)
  to suggest the use of `when` instead of a single if statement inside
  a pattern match body. This resulted in the creation
  of a new check `WhenShouldBeUsed` to enforce the use of this new feature.

### 1.6 Real Usage Examples in Large Projects

Review real usage examples of the new language feature in large projects.
This helps to identify potential issues and ensures that the checks
are aligned with practical use cases.
A list of representative projects can be found
[here](https://github.com/checkstyle/contribution/tree/master/checkstyle-tester)
in projects files.

### 1.7 Good Source of Best Practices

Identify a reliable source of best practices related to the new language feature.
This could include official language documentation and community-driven style guides
such as:

- [Oracle Java Magazine](https://blogs.oracle.com/javamagazine/)
- [JetBrains blogs](https://blog.jetbrains.com/)
- [Stackoverflow](https://stackoverflow.com/)

---

## 2. How to Create Tracker Issue

The tracker issue should document and share the results of the analysis
conducted according to the outlined procedures in this document.
It should include sections for each step of the analysis with the result
of all the actions done. These tracker issues should be created for each
new language feature, demonstrating the team's due diligence
in integrating the feature into Checkstyle.
See [#14961](https://github.com/checkstyle/checkstyle/issues/14961) and
[#14942](https://github.com/checkstyle/checkstyle/issues/14942) for reference.

---

## 3. How to Open Child Issues

Using the findings from the tracker issue, open child issues for each check that needs
to be updated or for a new check related to the new language feature.
This issue should be linked to the tracker issue of this feature.
It is good to follow the bug report template to aid in demonstrating the need for check updates.
See [#14963](https://github.com/checkstyle/checkstyle/issues/14963) and
[#14985](https://github.com/checkstyle/checkstyle/issues/14985) for reference.
