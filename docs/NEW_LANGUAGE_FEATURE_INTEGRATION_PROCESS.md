# New Language Feature Check Integration Process [WIP]

## Check Update Procedure

### Consider Similar Tokens
it's essential to analyze existing language constructs 
that share similarities with the new language feature. 
Once we've identified them, we will determine which checks support these features. 
these checks are most likely to require updates.

for `RECORD_DEF`, it was reasonable to look at all checks 
that had `CLASS_DEF` in their acceptable tokens.
### Java Enhancement Proposals (JEPS)
Review Java Enhancement Proposals (JEPS) related to the new feature. 
JEPS provide detailed information about the goals
and motivations behind new language features. 
This information can help us understand the feature better and figure out
which checks are most likely to be impacted.

**For example,** Unnamed variables (`_`) the goal of this feature according to JEP 
is to declare a variable that is not intend to be used.
JEP guides us to recognize the new role of the underscore for unnamed variables
and avoid flagging the non-use of such variables in modern code.
### Always Impacted Checks
Certain checks are most likely to be impacted by new language features.
Ensure these checks are updated to handle the new feature:
- [IllegalToken](https://checkstyle.org/checks/coding/illegaltoken.html)
- [IllegalTokenText](https://checkstyle.org/checks/coding/illegaltokentext.html) 
- [Indentation](https://checkstyle.org/checks/misc/indentation.html#Indentation)
- [Whitespace](https://checkstyle.org/checks/whitespace/index.html)
### Real Usages Examples in Large Codebases
### Review Other Static Analysis Tools

## New Check Procedure
### Good Source of Best Practices
### Review Associated JEP's Recommendations
### Discover Similar Checks

## How to Create Tracker Issue
### Document Discovery Methods
### Share Results

## How to Open Child Issues
