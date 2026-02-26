# Contribution Guidelines

![](https://raw.githubusercontent.com/checkstyle/resources/master/img/checkstyle-logos/checkstyle-logo-260x99.png)

---------------------------------

**This guide serves to set clear expectations for everyone involved with the project so that
we can improve it together while also creating a welcoming space for everyone to participate.
Following these guidelines will help ensure a positive experience for contributors and
maintainers alike.**

:octocat: *Thanks for your interest in contributing to CheckStyle!* :octocat:

## Contents

- [Code of Conduct](#code-of-conduct)
- [Getting Started](#getting-started)
- [Report an Issue](#report-an-issue)
- [Build](#build)
- [Opening an Issue](#opening-an-issue)
- [Reporting Security Issues](#reporting-security-issues)
- [Submitting Pull Requests](#submitting-pull-requests)
- [Quality Matters](#quality-matters)
- [Code Review](#code-review)
- [Submitting Your Contribution](#submitting-your-contribution)
- [Google Summer of Code](#google-summer-of-code)
- [Asking Questions](#asking-questions)
- [Credits](#credits)

## Code of Conduct

This project and everyone participating in it is governed by the
    [CheckStyle Code of Conduct](/.github/CODE_OF_CONDUCT.md).

## Getting Started

- Please see the [CheckStyle Documentation](https://checkstyle.org/beginning_development.html)
  for information on how to get started with the project. This includes setting up your
  development environment, building the project, and running tests.
- Take a look at the [Contribution Guidelines](https://checkstyle.org/contributing.html) for
  on how to contribute to the project.
- Select an issue to work on from the
  [Issues Page](https://github.com/checkstyle/checkstyle/issues). We
  have a few issues labeled as
  https://github.com/checkstyle/checkstyle/labels/good%20first%20issue to
  help you get started. Please watch [videos](https://www.youtube.com/playlist?list=PLHM9s_lN4X0hzOQ0sUmGdroxW0HfREAqj)
  on how to setup local environment and send first fix.
- Once you get your first PR merged, you can move on to
  https://github.com/checkstyle/checkstyle/labels/good%20second%20issue,
  https://github.com/checkstyle/checkstyle/labels/good%20third%20issue,
  and finally https://github.com/checkstyle/checkstyle/labels/good%20fourth%20issue
  before taking up some bug fixes or features. Always make sure that the issue you select
  has the https://github.com/checkstyle/checkstyle/labels/approved label.
- When you decide which issue you would like to take up,
  please comment on the issue to let others know that you are working on it ("I am on it.").
  It is completely ok to change a mind, please try to remove comment.
  If you see such comment created long time ago but issue is still open and no Pull created, please
  feel free to make a comment that you will try to do it.

## Report An Issue

To report an issue please follow our practices page -
[How to report an bug and new module request?](https://checkstyle.sourceforge.io/report_issue.html)

### Build

The project follows a general maven layout and phases for its build.
Generated jars will be in folder `target`.

- Generate maven standard jar:

```bash
./mvnw clean install
```

- Generate maven standard jar and skip all validations/verifications:

```bash
./mvnw -P no-validations clean install
```

- Generate uber jar (checkstyle-all-X.XX.jar) to use our command line:

```bash
./mvnw -P assembly clean package
```

## Submitting Pull Requests

- **Read our pull request rules.** See [PR Rules](https://github.com/checkstyle/checkstyle/wiki/PR-rules).
- **Comment on the issue.** When you decide which issue you would like to take up,
  please comment on the issue to let others know that you are working on it ("I am on it.").
  Existing "I am on it" comments are a good indication that someone is already working on the
  issue, but these comments can be old or outdated; if a comment is a few weeks old with no
  activity, feel free to ask if the issue is still being worked on.
- **Read the Github docs.** Visit GitHub's [Pull Request Guide](https://help.github.com/en/github/collaborating-with-issues-and-pull-requests/about-pull-requests)
  for information on how to submit a pull request.
- **Follow the template.** Please follow the [CheckStyle Pull Request Template](https://github.com/checkstyle/checkstyle/blob/master/.github/PULL_REQUEST_TEMPLATE.md)
  that is provided in the pull request description when submitting a pull request.
- **Run maven build locally.** `mvn clean verify` should pass on your local before
  submitting a pull request.
- **Keep the PR small.** If you are working on a large feature, consider breaking it up into
  smaller PRs that can be reviewed and merged independently. This makes it easier for
  reviewers to understand the changes and for maintainers to merge the PR.

## Quality Matters

We use a set of development tools to ensure that
the quality of our code is kept at a high level.
Like most projects today, we use JUnit to test our code.
However, we do take this one step further and measure
the coverage of our unit tests using [JaCoCo](https://www.eclemma.org/jacoco/).
This means it is not sufficient to pass all tests,
but the tests should ideally execute each line in the code,
code coverage should be 100%.
To generate the JaCoCo report, run the Maven command:

```bash

./mvnw clean test jacoco:restore-instrumented-classes jacoco:report@default-report .

```

Check the results of the report in
target/site/jacoco/index.html in the project's root folder.
Besides using unit testing, we obviously
also use checkstyle to check its own code.
The Maven command `./mvnw clean verify` must pass without any errors.

If you add new end user features (Check, Filter, ....), remember
to document them in JavaDoc of java classes and xdoc files
that are used to generate that site.
Please recheck the site and all bundles generated by `./mvnw clean site`

We require regression testing for most functional changes,
especially for check modules.
See our
[regression testing tool documentation](https://github.com/checkstyle/contribution/tree/master/checkstyle-tester#checkstyle-tester)
for details.

## Submitting Your Contribution

Once you have made sure that your changes pass the Maven command
`./mvnw clean verify`, the code coverage is of
high standard and everything is documented,
then you are ready to submit your work.

Please create a
[Pull Request](https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/proposing-changes-to-your-work-with-pull-requests/about-pull-requests)
for your contribution.
In the Pull Request description, add any
explanations of the implementation nuances.
Please read the Pull Request template for more requirements. ATTENTION:
Please verify that the Pull Request contains ONLY
your intended changes and is based on the
most recent HEAD of our master branch.

### Attention

The commit message must adhere to a certain format. It should be "Issue #Number:
Brief single-line message", where NUMBER is the issue number at GitHub
(see [an example](https://github.com/checkstyle/checkstyle/commit/9303fd9d971eb55cdfd62686ba2f5698edb2c83e)).
Small changes of configuration files, documentation fixes, etc.
can be contributed by starting the commit message with one of
"minor:", "config:", "infra:", "doc:", "dependency:" or "spelling:",
followed by a space and a brief single-line message.
"supplemental:" is used for PRs/commits that will support other,
more significant changes with format as
"supplemental: .... for Issue #XXXX" where "XXXX"
refers to the issue that requires this supplemental commit.
After submitting a Pull Request, it will be
automatically checked by our continuous integration (CI) systems,
including GitHub Workflows, CircleCI, and Azure Pipelines.
Therefore, please recheck after some minutes
that the CIs didn't find any issues with your changes.
If there are issues, please fix them by amending commit
(not by separate commit) and provide an
updated version of the same Pull Request.
At times we are busy with our day jobs. This means
that you might not always get an immediate answer.
You are not being ignored, and we value your work -
we might just be too busy to review your code,
especially if it is a bit complex.
If you don't get a response within two weeks,
feel free to send a reminder email about your tracker item.

## Code Review

All submissions, including submissions by project members, require review. We use GitHub pull
requests for this purpose. Consult the [GitHub Help](https://help.github.com/en/github/collaborating-with-issues-and-pull-requests/about-pull-request-reviews)
for more information on pull request reviews.

Here are some general guidelines to follow when submitting a pull request:

- **Reply to comments.** If a reviewer asks for changes, reply to each
  (and every) comment with discussion or follow up questions, or let the reviewer know
  that you have addressed their concerns ("done").
- **Be patient.** Reviewing PRs takes time. If a reviewer hasn't responded in a week or so,
  feel free to ping them. If you are a reviewer and you need more time to review a PR, please
  let the submitter know.
- **Be kind.** Remember that everyone involved in the project is a human being. Be kind and
  respectful in your comments and reviews.
- **Be open to feedback.** If a reviewer asks for changes, be open to their feedback. Remember that
  the goal is to improve the project, and feedback is an important part of that process.

## Opening an Issue

A great way to contribute to the project is to create a detailed issue when you encounter
a problem or would like to suggest a feature. We always appreciate a well-written,
thorough issue description. :brain:

Some points to consider when opening an issue:

- **Make sure you are using the latest Checkstyle version.**
  Before [Opening an Issue](https://github.com/checkstyle/checkstyle/issues),
  check if you are using the latest version of the project,
  [found here](https://github.com/checkstyle/checkstyle/releases). If you are not up-to-date,
  check to see if updating to the latest release fixes your issue.
- **Do not open a duplicate feature request.** Search for existing feature requests first. If you
  find your feature (or one very similar) previously requested, comment on that issue.
- **Fully complete the provided issue template.** The bug report and feature request templates
  specify all the information we need to quickly and efficiently address your issue. Be clear,
  concise, and descriptive.
  Provide as much information as you can, including steps to reproduce, stack traces, etc.
- **Use [GitHub-flavored Markdown](https://help.github.com/en/github/writing-on-github/basic-writing-and-formatting-syntax).**
  Especially put code blocks and console outputs in backticks (```). This improves readability.
- As soon as one of admins of our project approved your idea you are good
  to start implementation and you will be welcome with final code contribution.
  Please do not expect that we will accept any code that you send to us.
  Example of ideal [issue description](https://github.com/checkstyle/checkstyle/issues/258),
  and how it is commented on fix [Pull Request](https://github.com/checkstyle/checkstyle/pull/601).

## Reporting Security Issues

**Do not file a public issue for security vulnerabilities.** Please contact the
maintainers directly.
see the [Security Policy](https://github.com/checkstyle/checkstyle/blob/master/SECURITY.md)
for more information.

## Google Summer of Code

Please see the [GSoC Participant Guide](https://github.com/checkstyle/checkstyle/blob/master/.github/GSOC.md).

## Asking Questions

See our [Discussions Page](https://github.com/checkstyle/checkstyle/discussions). In short, GitHub
issues are not the appropriate place to debug your specific project, but should be reserved
for filing bugs and feature requests. You can also visit our
[Google Groups Forum](https://groups.google.com/g/checkstyle-devel)

## Credits

*This document was inspired by work from the following communities:*

- [CocoaPods](https://github.com/CocoaPods/CocoaPods/blob/master/CONTRIBUTING.md)
- [Docker](https://github.com/moby/moby/blob/master/CONTRIBUTING.md)
- [.github](https://github.com/jessesquires/.github)
