# Workflows

[regression-report.yml](#regression-report-workflow---regression-reportyml)

--------

## Regression Report workflow - regression-report.yml

This workflow allows user/contributor to trigger regression/diff report
for changes that they have in Pull Request(PR).

Workflow does:

- takes configurations from user's PR comment or/and PR description,
- builds checkstyle from main branch,
- run checkstyle validation for user defined list of project with user defined configs
- stores results of validation
- builds checkstyle from PR branch,
- run checkstyle validation for user defined list of project with user defined configs
- stores results of validation
- compares results of validation from both execution, generate html report
- share html report link back to PR

There are few way to trigger diff report.

### Diff Report by configuration in Input file

Make a comment in PR:

```txt
GitHub, generate report by config from InputXxxxxx.java
```

or

```txt
GitHub, generate report by config from some/path/InputXxxxxx.java
```

Workflow will find InputXxxxxx.java in repository, take config from it and will use for diff report
generation. Workflow will use default list of projects that is located at
[test-configs repository](https://github.com/checkstyle/test-configs/blob/main/extractor/src/main/resources/list-of-projects.yml).

### Diff Report by configuration at test-configs repository

Make a comment in PR:

```txt
GitHub, generate report for {{folder in https://github.com/checkstyle/test-configs}}
```

Workflow will download config.xml and list-of-projects.yml from
[test-configs repository](https://github.com/checkstyle/test-configs),
use then for diff report generation.

### Diff Report by configuration in Pull Request Description

Add the links of the config files in the PR description as described at
[checkstyle-tester](https://github.com/checkstyle/contribution/blob/main/checkstyle-tester/README.md#executing-generation-using-github-action)

Make a comment in PR:

```txt
Github, generate report for configs in PR description
```

### Configure saved-replies to ease typing trigger comment

If you plan to frequently use regression/diff report, it is recommended to use
["Saved Reply"](https://docs.github.com/en/get-started/writing-on-github/working-with-saved-replies/creating-a-saved-reply)
GitHub feature of your GitHub account.

Usage:
[by button in web UI](https://docs.github.com/en/get-started/writing-on-github/working-with-saved-replies/using-saved-replies),
[by slash command](https://docs.github.com/en/issues/tracking-your-work-with-issues/using-issues/about-slash-commands)
