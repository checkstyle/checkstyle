Mandatory to understand and do:
0) Issue you are trying to fix/resolve has to have "approved" label.
1) Put in description of Pull Request reference to issue if it exists. Example: "Issue: #XXXXXX"
2) Commit message should adhere to the following rules:
   a) Must match one of the following patterns:\n"
      ^Issue #\\d+: .*$
      ^Pull #\\d+: .*$
      ^(minor|config|infra|doc|spelling): .*$
   b) It contains only one line of text
   c) Must not end with a period, space, or tab

To avoid multiple iterations of fixes and CIs failures, please read http://checkstyle.sourceforge.net/contributing.html

ATTENTION: We are not merging Pull Requests that not passing our CIs, but we help to resole issues.

Thanks for reading, remove whole this message and type what you need.
