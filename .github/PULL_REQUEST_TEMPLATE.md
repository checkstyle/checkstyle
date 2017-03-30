Mandatory to understand and do:
0) The issue you are trying to fix/resolve has to have the "approved" label.
1) Put in the description of Pull Request the reference to an issue if it exists. Example: "Issue: #XXXXXX"
2) Commit message should adhere to the following rules:
   a) Must match one of the following patterns:\n"
      ^Issue #\\d+: .*$
      ^Pull #\\d+: .*$
      ^(minor|config|infra|doc|spelling): .*$
   b) It contains only one line of text
   c) Must not end with a period, space, or tab
   d) Commit message should be less than or equal to 200 characters

To avoid multiple iterations of fixes and CIs failures, please read http://checkstyle.sourceforge.net/contributing.html

ATTENTION: We are not merging Pull Requests that are not passing our CIs, but we will help to resolve issues.

Thanks for reading, remove whole this message and type what you need.
