# PLEASE READ BEFORE REMOVING

**Rules:**

1. **Issue Requirement**
   - The issue you are trying to fix/resolve **must** have the `"approved"` label.
   - If an issue exists, reference it in the Pull Request description:
     Example: `"Issue: #XXXXXX"`

2. **Commit message** should adhere to the following rules:
   - MUST match any one of the following patterns:
     ```
     ^Issue #\d+: .*$
     ^Pull #\d+: .*$
     ^(minor|config|infra|doc|spelling|dependency): .*$
     ```
   - MUST contain only one line of text
   - MUST NOT end with a period, space, or tab
   - MUST be less than or equal to 200 characters

To avoid multiple iterations of fixes and CI failures, please read
[Contribution Guide](https://checkstyle.org/contributing.html).

**ATTENTION:** Pull Requests that do not pass our CI checks will not be merged,
but we will help to resolve issues.

---
Thanks for reading, feel free to remove this whole message and type what you need.
