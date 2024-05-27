# New Language Feature Check Integration Process [WIP]
## Check Update Procedure
### Consider Similar Tokens
Identify existing tokens that are similar to the new language feature tokens.
This comparison helps to determine which existing checks may be relevant
and require updates to support the new token.

**Examples**
- for `RECORD_DEF`, it was reasonable to look at all checks 
that had `CLASS_DEF` in their acceptable tokens.
- for `TEXT_BLOCK_CONTENT`, it was reasonable to look at all checks
that had `STRING_LITERAL` in their acceptable tokens.
### Java Enhancement Proposals (JEPS)
Review Java Enhancement Proposals (JEPS) related to the new feature. 
JEPS provide detailed information about the goals
and motivations behind new language features. 
This information can help us understand the feature better and figure out
which checks are most likely to be impacted.

**Example**
- Unnamed variables (`_`), JEP guides us to recognize the new role of the
underscore for unnamed variables and avoid flagging the non-use of such variables.
Consequently, we should update `UnusedLocalVariableCheck` to ensure it does not 
incorrectly violate underscore variables.
### Always Impacted Checks
Certain checks are most likely to be impacted by new language features.
It is mandatory to analyze impact on the following checks:
- [IllegalToken](https://checkstyle.org/checks/coding/illegaltoken.html)
- [IllegalTokenText](https://checkstyle.org/checks/coding/illegaltokentext.html) 
- [Indentation](https://checkstyle.org/checks/misc/indentation.html#Indentation)
- [Whitespace](https://checkstyle.org/checks/whitespace/index.html)
### Review Other Static Analysis Tools
Examining how other static analysis tools handle new language features
can provide valuable insights into best practices and potential pitfalls.

**Example**
- Suppose Java 17 or higher, IntelliJ IDEA introduced an inspection rule to detect 
redundant `strictfp` modifiers. In this case, we should consider updating `RedundantModifierCheck`
to have a similar behaviour.
### Real Usages Examples in Large Projects
Review real usage examples of the new language feature in large projects.
This helps to identify potential issues and ensures that the checks
are aligned with practical use cases.
List of representative projects can be found 
[here](https://github.com/checkstyle/contribution/blob/master/checkstyle-tester/github-action-projects1.properties)
## New Check Procedure
### Good Source of Best Practices
Identify a reliable source of best practices related to the new language feature. 
This could include official language documentation, community-driven style guides.
We may consider leveraging the OpenJDK mailing list as a valuable resource
for gathering conventions and guidelines related to the new language feature.
### Review Associated JEP's Recommendations
Refer to the associated JEPs for the new language feature.
JEPs provide detailed insights into the design decisions, motivations, and intended usage of the feature.
Often times, JEPs may solve a particular problem in the language,
and we can create checks to suggest to use these new features
Pay close attention to any recommendations provided in the JEPs,
as they can inform the development of effective new checks

**Example**
- Pattern Matching for instanceof, where the newer pattern matching feature provides
a more concise and type-safe alternative to traditional casting. We can create check
to suggest to use this new feature
### Discover Similar Checks
## How to Create Tracker Issue
### Document Discovery Methods
After thoroughly researching the impact of the new language feature using the previously 
mentioned method, document all relevant findings, including impacted checks,
possible static analysis coverage (new Checks), and best practices.
### Share Results
After completing the discovery process for each new language feature,
open tracker issues to share the results obtained from all methods this includes 
checks might need to be updated or new Checks 
## How to Open Child Issues
Open child issues for each check that needs to be updated or for new Check related
to the new language feature. This issue should be linked to the tracker issue of this feature.
It is good to follow the bug report template to aid in demonstration 
of need for check updates.
