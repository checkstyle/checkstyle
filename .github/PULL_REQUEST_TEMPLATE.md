# PLEASE READ before removing

Rules:
1) The issue you are trying to fix/resolve has to have the "approved" label.
2) Put in the description of Pull Request the reference to an issue if it exists.
   Example: "Issue: #XXXXXX"
3) Commit message should adhere to the following rules:
   a) MUST match one of the following patterns:
      ^Issue #\\d+: .*$
      ^Pull #\\d+: .*$
      ^(minor|config|infra|doc|spelling|dependency|supplemental): .*$
   b) MUST contain only one line of text
   c) MUST NOT end with a period, space, or tab
   d) MUST be less than or equal to 200 characters

To avoid multiple iterations of fixes and CIs failures, please read
https://checkstyle.org/contributing.html

_______________________________________________________

Check regression report generation template (see details [here](https://github.com/checkstyle/contribution/tree/master/checkstyle-tester#report-generation)):

Diff Regression config: {{URI to my_checks.xml}

Diff Regression patch config: {{URI to patch_config.xml}} (optional)

Diff Regression projects: {{URI to projects-to-test-on.properties}} (optional)

_______________________________________________________

ATTENTION: We are not merging Pull Requests that are not passing our CIs,
but we will help to resolve issues.

Thanks for reading, remove whole this message and type what you need.
