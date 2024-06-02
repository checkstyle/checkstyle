# New Language Feature Check Integration Process [WIP]

The ability to parse new language features must be implemented and
merged before following this document. This document outlines the procedures
for integrating new language features into Checkstyle,
by updating existing checks and creating new ones.

## Check Update Procedure

Updating check modules to support new language features aligns Checkstyle
with current best practices in the Java community
and contributes to the project's ongoing evolution.

### Java Enhancement Proposals (JEPS)

Review Java Enhancement Proposals (JEPS) related to the new feature.
JEPS provide detailed information about the goals
and motivations behind new language features.
This information can help us understand the feature better and figure out
which checks are most likely to be impacted.

**Examples**:

- For unnamed variables (`_`), the JEP guides us to recognize the new role of the
  underscore for unnamed variables and avoid flagging the non-use of such variables.
  Consequently, we should update `UnusedLocalVariableCheck` to ensure it does not
  incorrectly violate unnamed variables.

### Consider Similar and Related Tokens

Identify existing tokens that are similar to the new language feature tokens.
This comparison helps to determine which existing checks may be relevant
and require updates to support the new token. Additionally,
we should consider the impact that a new child token may have
on existing checks.

**Examples**:

- When the `RECORD_DEF` token was introduced, it was reasonable to look at all checks
  that had `CLASS_DEF` in their acceptable tokens.

- When the `TEXT_BLOCK_CONTENT` token was introduced, it was reasonable to look at all checks
  that had `STRING_LITERAL` in their acceptable tokens.

- When switch expressions were introduced, it was reasonable to look at all checks
  that deals with expressions because `LITERAL_SWITCH` may now fall under `EXPR` token.
  This requires analyzing the impact of this new child token on existing checks,
  such as `InnerAssignmentCheck`

### Frequently Impacted Checks

Certain checks are most likely to be impacted by new language features.
We should analyze the impact on the following frequently affected checks:

- [IllegalToken](https://checkstyle.org/checks/coding/illegaltoken.html)
- [IllegalTokenText](https://checkstyle.org/checks/coding/illegaltokentext.html)
- [Indentation](https://checkstyle.org/checks/misc/indentation.html#Indentation)
- [Whitespace](https://checkstyle.org/checks/whitespace/index.html)

### Review Other Static Analysis Tools

Examining how other static analysis tools handle new language features
can provide valuable insights into best practices and potential pitfalls.
A list to a few of the popular ones:

- [IntelliJ Inspections](https://www.jetbrains.com/help/idea/code-inspection.html)
- [PMD](https://pmd.github.io/)
- [SonarQube](https://www.sonarqube.org/)

**Examples**:

- IntelliJ IDEA introduced an inspection rule for Java 17 or higher to detect
  redundant `strictfp` modifiers. This resulted in the analysis of `RedundantModifierCheck`
  to verify if we needed to update it.

### Real Usage Examples in Large Projects

Review real usage examples of the new language feature in large projects.
This helps to identify potential issues and ensures that the checks
are aligned with practical use cases.
A list of representative projects can be found
[here](https://github.com/checkstyle/contribution/blob/master/checkstyle-tester/github-action-projects1.properties).

## New Check Procedure

Creating new checks for new language features helps us establish
best practices within the community, ensuring that developers
utilize these features effectively.

### Good Source of Best Practices

Identify a reliable source of best practices related to the new language feature.
This could include official language documentation and community-driven style guides
such as:

- [Oracle Java Magazine](https://blogs.oracle.com/javamagazine/)
- [JetBrains blogs](https://blog.jetbrains.com/)
- [Stackoverflow](https://stackoverflow.com/)

### Review Associated JEP's Recommendations

Refer to the associated JEPs for the new language feature.
JEPs provide detailed insights into the design decisions,
motivations and intended usage of the feature.
Oftentimes, JEPs may solve a particular problem in the language,
and we can create checks to suggest the use of these new features.
Pay close attention to any recommendations provided in the JEPs
as they can inform the development of effective new checks.

**Examples**:

- Pattern Matching for instanceof, where the newer pattern matching feature provides
  a more concise and type-safe alternative to traditional casting.
  Ideally, we would consider creating a check
  to suggest the use of this new feature if we see the typical `if X instanceof Y`,
  then typecasting pattern.

### Discover Similar Checks

Look for existing checks that address concepts
similar to those introduced by the new language feature.
These checks serve as references for designing and implementing new checks.

**Examples**:

- **Identifiers and Naming Conventions**: If the new feature introduces
  new tokens involving identifiers, explore existing checks related to naming conventions.
  We can create a new similar check to enforce naming conventions for the new feature.

## How to Create Tracker Issue

The tracker issue should document and share the results of the analysis
conducted according to the outlined procedures in this document.
It should include sections for each step of the analysis with the result
of all of our actions. These tracker issues should be created for each
new language feature, demonstrating our due diligence
in integrating the feature into Checkstyle.

## How to Open Child Issues

Using your findings from the tracker issue, open child issues for each check that needs
to be updated or for a new check related to the new language feature.
This issue should be linked to the tracker issue of this feature.
It is good to follow the bug report template to aid in demonstrating the need for check updates.
