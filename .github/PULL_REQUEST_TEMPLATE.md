PLEASE READ before removing!!

Rules:
1) The issue you are trying to fix/resolve has to have the "approved" label.
2) Put in the description of Pull Request the reference to an issue if it exists.
   Example: "Issue: #XXXXXX"
3) Commit message should adhere to the following rules:
   a) MUST match one of the following patterns:
      ^Issue #\\d+: .*$
      ^Pull #\\d+: .*$
      ^(minor|config|infra|doc|spelling): .*$
   b) MUST contain only one line of text
   c) MUST NOT end with a period, space, or tab
   d) MUST be less than or equal to 200 characters

To avoid multiple iterations of fixes and CIs failures, please read
https://checkstyle.org/contributing.html

ATTENTION: We are not merging Pull Requests that are not passing our CIs.
The CIs are not redundant, but each perform individual tasks. The requirement
for PRs is to pass them all. Travis CI on your GitHub fork is limited to master
you need to patch the configuration if you insist on using Travis CI to check
your PRs. Of course, we will help you to resolve issues.

Thanks for reading, remove whole this message and type what you need.
