# Contributing Guidelines

![](https://raw.githubusercontent.com/checkstyle/resources/master/img/checkstyle-logos/checkstyle-logo-260x99.png)

**This guide serves to set clear expectations for everyone involved with the project so that
we can improve it together while also creating a welcoming space for everyone to participate.
Following these guidelines will help ensure a positive experience for contributors and
maintainers alike.**

:octocat: *Thanks for your interest in contributing to CheckStyle!* :octocat:

### Contents
- [Getting Started](#rocket-getting-started)
- [Code of Conduct](#book-code-of-conduct)
- [Asking Questions](#bulb-asking-questions)
- [Opening an Issue](#inbox_tray-opening-an-issue)
- [Reporting Security Issues](#lock-reporting-security-issues)
- [Submitting Pull Requests](#small_airplane-submitting-pull-requests)
- [Code Review](#white_check_mark-code-review)
- [Credits](#coin-credits)

## :rocket: Getting Started

 - Please see the [CheckStyle Documentation](https://checkstyle.org/beginning_development.html)
    for information on how to get started with the project. This includes setting up your development
    environment, building the project, and running tests.
 - Take a look at the [Contribution Guidelines](https://checkstyle.org/contributing.html) for
    on how to contribute to the project.
 - Select an issue to work on from the [Issues Page](https://github.com/checkstyle/checkstyle/issues). We
    have a few issues labeled as https://github.com/checkstyle/checkstyle/labels/good%20first%20issue to
    help you get started.

## :book: Code of Conduct

WIP

## :bulb: Asking Questions

See our [Discussions Page](https://github.com/checkstyle/checkstyle/discussions). In short, GitHub
issues are not the appropriate place to debug your specific project, but should be reserved
for filing bugs and feature requests.

## :inbox_tray: Opening an Issue

Before [Opening an Issue](https://github.com/checkstyle/checkstyle/issues),
check if you are using the latest version of the project,
[found here](https://github.com/checkstyle/checkstyle/releases). If you are not up-to-date,
see if updating to the latest release fixes your issue.

A great way to contribute to the project is to create a detailed issue when you encounter a problem or would
like to suggest a feature. We always appreciate a well-written, thorough issue description. :brain:

Some things to consider when opening an issue:
- **Fully complete the provided issue template.** The bug report and feature request templates specify
    all the information we need to quickly and efficiently address your issue. Be clear, concise, and descriptive.
    Provide as much information as you can, including steps to reproduce, stack traces, etc.
- **Use [GitHub-flavored Markdown](https://help.github.com/en/github/writing-on-github/basic-writing-and-formatting-syntax).**
    Especially put code blocks and console outputs in backticks (```). This improves readability.
- **Do not open a duplicate feature request.** Search for existing feature requests first. If you
    find your feature (or one very similar) previously requested, comment on that issue.

## :lock: Reporting Security Issues

**Do not file a public issue for security vulnerabilities.** Please contact the maintainers directly.

## :small_airplane: Submitting Pull Requests

 - **Read the Github docs.** Visit GitHub's [Pull Request Guide](https://help.github.com/en/github/collaborating-with-issues-and-pull-requests/about-pull-requests)
    for information on how to submit a pull request.
 - **Follow the template.** Please follow the [CheckStyle Pull Request Template](https://github.com/checkstyle/checkstyle/blob/master/.github/PULL_REQUEST_TEMPLATE.md)
    that is provided in the pull request description when submitting a pull request.
 - **Run maven build locally.** `mvn clean verify` should pass on your local before submitting a pull request.

## :white_check_mark: Code Review

All submissions, including submissions by project members, require review. We use GitHub pull
requests for this purpose. Consult the [GitHub Help](https://help.github.com/en/github/collaborating-with-issues-and-pull-requests/about-pull-request-reviews)
for more information on pull request reviews.

Here are some general guidelines to follow when submitting a pull request:
 - **Reply to comments.** If a reviewer asks for changes, make the changes and reply to each (and every)
    comment to let the reviewer know that you have addressed their concerns.
 - **Keep the PR small.** If you are working on a large feature, consider breaking it up into smaller
    PRs that can be reviewed and merged independently. This makes it easier for reviewers to understand
    the changes and for maintainers to merge the PR.
 - **Be patient.** Reviewing PRs takes time. If a reviewer hasn't responded in a few days, feel free to
    ping them. If you are a reviewer and you need more time to review a PR, please let the submitter know.
 - **Be kind.** Remember that everyone involved in the project is a human being. Be kind and respectful
    in your comments and reviews.
 - **Be open to feedback.** If a reviewer asks for changes, be open to their feedback. Remember that
    the goal is to improve the project, and feedback is an important part of that process.

## :coin: Credits

*This document was inspired by work from the following communities:*

- [CocoaPods](https://github.com/CocoaPods/CocoaPods/blob/master/CONTRIBUTING.md)
- [Docker](https://github.com/moby/moby/blob/master/CONTRIBUTING.md)
- [.github](https://github.com/jessesquires/.github)
