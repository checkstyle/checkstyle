# PLEASE READ BEFORE REMOVING

**Rules:**

- **Issue Requirement**
   - The issue you are trying to fix/resolve **must** have the `"approved"` label.
   - If an issue exists, reference it in the Pull Request description:
     Example: `"Issue: #XXXXXX"`
- **Run basic validation on your local**
   - Run `mvnw clean verify` before PR creation, it must pass without any errors.
- **Commit message** should adhere to the following rules:
   - MUST match any one of the following patterns:

     ```
     ^Issue #\d+: .*$
     ^Pull #\d+: .*$
     ^(minor|config|infra|doc|spelling|dependency|supplemental): .*$
     ```

   - MUST contain only one line of text
   - MUST NOT end with a period, space, or tab
   - MUST be less than or equal to 200 characters
   - Use `Issue #XXXXX:` for a defect fix or a change of behaviour, so it is
     picked up by the release notes. For the meaning of each prefix and an
     example of each, see [Submitting your
     contribution](https://checkstyle.org/contributing.html#Submitting_your_contribution)

To avoid multiple iterations of fixes and CI failures, please read
[Contribution Guide](https://checkstyle.org/contributing.html).

**ATTENTION:** Pull Requests that do not pass our CI checks will not be merged,
but we will help to resolve issues.

---
Thanks for reading, feel free to remove this whole message and type what you need.
