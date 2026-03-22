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
- [Opening an Issue](#opening-an-issue)
- [Reporting Security Issues](#reporting-security-issues)
- [Submitting Pull Requests](#submitting-pull-requests)
- [Code Review](#code-review)
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
