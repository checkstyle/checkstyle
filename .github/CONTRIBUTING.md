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

- Please see the [Build Instructions][build-instructions]
  for information on how to get started with the project. This includes setting up your
  development environment, building the project, and running tests.
- Select an issue to work on from the [Issues Page][issues-page]. We
  have a few issues labeled as [good first issue][goodFirst-Issue] to
  help you get started. Please watch [videos][videos-playlist] on how to
  setup local environment and send first fix.
- Once you get your first PR merged, you can move on to [good second issue][goodSecond-Issue],
  [good third issue][goodThird-Issue], [good fourth issue][goodFourth-Issue],
  and finally [good fifth issue][goodFifth-Issue] before taking up some bug fixes or features.
  Always make sure that the issue you select has the [approved][Approved-Label] label.
- When you decide which issue you would like to take up,
  please comment on the issue to let others know that you are working on it ("I am on it.").
  It is completely ok to change a mind, please try to remove comment.
  If you see such comment created long time ago but issue is still open and no Pull created, please
  feel free to make a comment that you will try to do it.

## Submitting Pull Requests

- **Read our pull request rules.** See [PR Rules][pr-rules].
- **Comment on the issue.** When you decide which issue you would like to take up,
  please comment on the issue to let others know that you are working on it ("I am on it.").
  Existing "I am on it" comments are a good indication that someone is already working on the
  issue, but these comments can be old or outdated; if a comment is a few weeks old with no
  activity, feel free to ask if the issue is still being worked on.
- **Read the Github docs.** Visit GitHub's
  [Pull Request Guide][pull-request-guide] for information on how to
  submit a pull request.
- **Follow the template.** Please follow the
  [CheckStyle Pull Request Template][pr-template] that is provided in the
  pull request description when submitting a pull request.
- **Write a proper commit message.** Please follow the rules in
  [Commit Messages](#commit-messages) below. The message is validated in CI,
  and a pull request is expected to contain a single commit.
- **Run maven build locally.** `mvn clean verify` should pass on your local before
  submitting a pull request.
- **Keep the PR small.** If you are working on a large feature, consider breaking it up into
  smaller PRs that can be reviewed and merged independently. This makes it easier for
  reviewers to understand the changes and for maintainers to merge the PR.

### Commit Messages

Every commit message is a single line of text, no longer than 200 characters, and
it must not end with a period, a space, or a tab. The commit must not have a body
or trailers (like `Co-Authored-By:`), as the message is validated by
`CommitValidationTest`. Start the message with one of the prefixes below,
followed by a space and a brief description of the change. Write the description
in the imperative form ("Add", "Fix", "Remove", "Update") and keep it specific,
so a reader understands the change without opening the pull request.

A change that fixes a defect or changes behaviour must reference its issue, so
the change is picked up by the release notes. Never use `minor:` for a defect.

- `Issue #<number>:` is for a change that resolves the referenced issue. This
  is the format for bug fixes, new features, and every change of behaviour.
  Example: [Issue #21861: Migrate JavadocVariable XPath tests to inline
  configuration][commit-example-issue]
- `Pull #<number>:` is for a change that is tracked by the pull request itself,
  when there is no issue for it. The number must be the number of the pull
  request that contains the commit.
  Example: [Pull #22293: Report outdated ArchUnit suppressions for items that
  already follow the rule][commit-example-pull]

Small changes that do not resolve an issue can be contributed with one of the
following prefixes instead:

- `minor:` is for small changes that do not fit any other prefix, like removing
  stale suppressions or small updates to contributor facing files.
  Example: [minor: Update pull request template with validation
  instructions][commit-example-minor]
- `config:` is for changes of configuration files, like the project
  configuration of checks, continuous integration services, or ignore files.
  Example: [config: Ignore stray jacoco.exec that can land at the repo
  root][commit-example-config]
- `infra:` is for changes of the infrastructure that keeps the project running,
  like workflow files, continuous integration scripts, and release tooling.
  Example: [infra: select latest Eclipse compiler release by version in
  eclipse-static-analysis][commit-example-infra]
- `doc:` is for changes of documentation only, like site pages, guides, and
  release notes.
  Example: [doc: Add documentation for
  checkstyle-openrewrite-recipes][commit-example-doc]
- `dependency:` is for updates of versions of dependencies and plugins.
  Example: [dependency: bump jacoco.plugin.version from 0.8.15 to
  0.8.16][commit-example-dependency]
- `spelling:` is for fixes of spelling mistakes only, in documentation,
  comments, or messages.
  Example: [spelling: suppresion -> suppression][commit-example-spelling]
- `supplemental:` is for additional changes that supplement the work on an
  issue but are not the fix for the issue itself, like a cleanup of violations
  or of test inputs that was noticed while the main change was in review. Put
  the number of the issue at the end of the message.
  Example: [supplemental: resolve JavadocUtilizingTrailingSpace violations
  #21890][commit-example-supplemental]

Starting a commit message with a type is an idea that is shared with other
projects. The [Conventional Commits][conventional-commits] specification
describes such a convention and is useful background reading. Checkstyle does
not use the types of that specification (like `feat:` or `fix:`), only the
formats listed above are accepted here.

## Code Review

All submissions, including submissions by project members, require review. We use GitHub pull
requests for this purpose. Consult the
[GitHub Help][github-help-pr-reviews] for more information on pull
request reviews.

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
  Before [Opening an Issue][opening-issue], check if you are using the
  latest version of the project, [found here][releases]. If you are not
  up-to-date, check to see if updating to the latest release fixes your
  issue.
- **Do not open a duplicate feature request.** Search for existing feature requests first. If you
  find your feature (or one very similar) previously requested, comment on that issue.
- **Fully complete the provided issue template.** The bug report and feature request templates
  specify all the information we need to quickly and efficiently address your issue. Be clear,
  concise, and descriptive.
  Provide as much information as you can, including steps to reproduce, stack traces, etc.
- **Use [GitHub-flavored Markdown][github-markdown].**
  Especially put code blocks and console outputs in backticks (```). This
  improves readability.

## Reporting Security Issues

**Do not file a public issue for security vulnerabilities.** Please contact the
maintainers directly.
see the [Security Policy][security-policy] for more information.

## Google Summer of Code

Please see the [GSoC Participant Guide][gsoc-guide].

## Asking Questions

See our [Discussions Page][discussions]. In short, GitHub
issues are not the appropriate place to debug your specific project, but
should be reserved for filing bugs and feature requests. You can also
visit our [Google Groups Forum][google-groups].

## Credits

*This document was inspired by work from the following communities:*

- [CocoaPods](https://github.com/CocoaPods/CocoaPods/blob/master/CONTRIBUTING.md)
- [Docker](https://github.com/moby/moby/blob/master/CONTRIBUTING.md)
- [.github](https://github.com/jessesquires/.github)

[issues-page]:
https://github.com/checkstyle/checkstyle/issues
[goodFirst-Issue]:
https://github.com/checkstyle/checkstyle/labels/good%20first%20issue
[videos-playlist]:
https://www.youtube.com/playlist?list=PLHM9s_lN4X0hzOQ0sUmGdroxW0HfREAqj
[goodSecond-Issue]:
https://github.com/checkstyle/checkstyle/labels/good%20second%20issue
[goodThird-Issue]:
https://github.com/checkstyle/checkstyle/labels/good%20third%20issue
[goodFourth-Issue]:
https://github.com/checkstyle/checkstyle/labels/good%20fourth%20issue
[goodFifth-Issue]:
https://github.com/checkstyle/checkstyle/labels/good%20fifth%20issue
[Approved-Label]:
https://github.com/checkstyle/checkstyle/labels/approved
[pr-rules]:
https://github.com/checkstyle/checkstyle/wiki/PR-rules
[pull-request-guide]:
https://help.github.com/en/github/collaborating-with-issues-and-pull-requests/about-pull-requests
[pr-template]:
https://github.com/checkstyle/checkstyle/blob/master/.github/PULL_REQUEST_TEMPLATE.md
[github-help-pr-reviews]:
https://help.github.com/en/github/collaborating-with-issues-and-pull-requests/about-pull-request-reviews
[opening-issue]:
https://github.com/checkstyle/checkstyle/issues
[releases]:
https://github.com/checkstyle/checkstyle/releases
[github-markdown]:
https://help.github.com/en/github/writing-on-github/basic-writing-and-formatting-syntax
[security-policy]:
https://github.com/checkstyle/checkstyle/blob/master/SECURITY.md
[gsoc-guide]:
https://github.com/checkstyle/checkstyle/blob/master/.github/GSOC.md
[discussions]:
https://github.com/checkstyle/checkstyle/discussions
[google-groups]:
https://groups.google.com/g/checkstyle-devel
[build-instructions]:
https://github.com/checkstyle/checkstyle/blob/master/docs/BEGINNING_DEVELOPMENT.md
[commit-example-issue]:
https://github.com/checkstyle/checkstyle/commit/0fa8737d14d44f8f139dbb4cfd108e54450c00c2
[commit-example-pull]:
https://github.com/checkstyle/checkstyle/commit/5d6b570ba9744c640f5b2c241818bb85fe8f3910
[commit-example-minor]:
https://github.com/checkstyle/checkstyle/commit/1db68a3e587291c8c042aeb0c17f2fc5dba7f3a5
[commit-example-config]:
https://github.com/checkstyle/checkstyle/commit/a934e12dee4b9063cd4b3e3dd8c1eb98afd67104
[commit-example-infra]:
https://github.com/checkstyle/checkstyle/commit/c1fbcb7a1b67faacb81d8514fa3fd06cc52453dd
[commit-example-doc]:
https://github.com/checkstyle/checkstyle/commit/e8009ae47faae2152c97e56b44c7d4a5e620b164
[commit-example-dependency]:
https://github.com/checkstyle/checkstyle/commit/0b7125bffb444037fbc2e63fff85916784756f01
[commit-example-spelling]:
https://github.com/checkstyle/checkstyle/commit/ee37567e7b96f4e385868b77f11d63a213718b35
[commit-example-supplemental]:
https://github.com/checkstyle/checkstyle/commit/8c0de8cacdf0d8caf9043a6762a510f4fea7afd1
[conventional-commits]:
https://www.conventionalcommits.org/en/v1.0.0/
