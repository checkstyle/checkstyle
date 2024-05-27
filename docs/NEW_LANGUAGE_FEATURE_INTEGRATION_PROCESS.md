# New Language Feature Check Integration Process [WIP]

This document outlines the procedures for integrating new language features into Checkstyle.
It includes guidelines for how to discover new checks and
existing checks that need updates to ensure that Checkstyle remains
aligned with language enhancements.

## Check Update Procedure

Updating check modules to support new language features aligns Checkstyle
with current best practices in the Java community
and contributes to the project's ongoing evolution.

### Consider Similar Tokens

Identify existing tokens that are similar to the new language feature tokens.
This comparison helps to determine which existing checks may be relevant
and require updates to support the new token.

**Examples**:

- When the `RECORD_DEF` token was introduced, it was reasonable to look at all checks
  that had `CLASS_DEF` in their acceptable tokens.

- When the `TEXT_BLOCK_CONTENT` token was introduced, it was reasonable to look at all checks
  that had `STRING_LITERAL` in their acceptable tokens.

### Java Enhancement Proposals (JEPS)

Review Java Enhancement Proposals (JEPS) related to the new feature.
JEPS provide detailed information about the goals
and motivations behind new language features.
This information can help us understand the feature better and figure out
which checks are most likely to be impacted.

**Examples**:

- Unnamed variables (`_`), JEP guides us to recognize the new role of the
  underscore for unnamed variables and avoid flagging the non-use of such variables.
  Consequently, we should update `UnusedLocalVariableCheck` to ensure it does not
  incorrectly violate unnamed variables.

### Always Impacted Checks

Certain checks are most likely to be impacted by new language features.
It is mandatory to analyze the impact on the following checks:

- [IllegalToken](https://checkstyle.org/checks/coding/illegaltoken.html)
- [IllegalTokenText](https://checkstyle.org/checks/coding/illegaltokentext.html)
- [Indentation](https://checkstyle.org/checks/misc/indentation.html#Indentation)
- [Whitespace](https://checkstyle.org/checks/whitespace/index.html)

### Review Other Static Analysis Tools

Examining how other static analysis tools handle new language features
can provide valuable insights into best practices and potential pitfalls.

**Examples**:

- IntelliJ IDEA introduced an inspection rule for Java 17 or higher to detect
  redundant `strictfp` modifiers. This resulted in the analysis of `RedundantModifierCheck`
  to verify if we needed to update it.

### Real Usage Examples in Large Projects

Review real usage examples of the new language feature in large projects.
This helps to identify potential issues and ensures that the checks
are aligned with practical use cases.
A list of representative projects can be found
[here](https://github.com/checkstyle/contribution/blob/master/checkstyle-tester/github-action-projects1.properties)

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

The tracker issue should document the results of the analysis
conducted according to the outlined procedures in this document.
It should include sections for each step of the analysis with the result
of all of our actions. These tracker issues should be created for each
new language feature, demonstrating our due diligence
in integrating the feature into Checkstyle.

### Document Discovery Methods

After thoroughly researching the impact of the new language feature using the previously
mentioned method, document all relevant findings, including impacted checks,
possible static analysis coverage (new checks), and best practices.

### Share Results

After completing the discovery process for each new language feature,
open tracker issues to share the results obtained from all methods, including
checks that might need to be updated or new checks.

## How to Open Child Issues

Open child issues for each check that needs to be updated or for a new check related
to the new language feature. This issue should be linked to the tracker issue of this feature.
It is good to follow the bug report template to aid in demonstrating
the need for check updates.