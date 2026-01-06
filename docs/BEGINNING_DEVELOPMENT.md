# Beginning Development Guide

![](https://raw.githubusercontent.com/checkstyle/resources/main/img/checkstyle-logos/checkstyle-logo-260x99.png)

---------------------------------

**This guide is for developers who want to contribute to Checkstyle. It will guide you through
everything you need to do to complete your first pull request.**

## Prerequisites

This guide assumes that you have some basic knowledge of
the command line for your operating system.

You must have the following installed on your local machine:

- [Java 21](https://docs.oracle.com/en/java/javase/21/install/overview-jdk-installation.html)
- [git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)

## Forking the repository

You must fork the repository to your own GitHub account. This will allow you to make
changes to the code and submit a pull request to the main repository.

Navigate to https://github.com/checkstyle/checkstyle/fork and click the "Create fork" button. It is
recommended to NOT rename the forked repository, and the rest of this guide assumes that
you have not renamed the forked repository.

## Setting up your local repository

Clone the repository to your local machine using the following command. You must
Replace `MY_USERNAME` with your GitHub username.

```bash
git@github.com:MY_USERNAME/checkstyle.git
```

Navigate to the root directory of the project and run the following command:

```bash
git remote add upstream https://github.com/checkstyle/checkstyle
````

This allows you to pull changes from the main repository into your local repository.

## Building the project

To build the project, navigate to the root directory of the project and run the following command:

```bash
mvn clean verify
```

This will build the project and run all the tests. Congratulations, you have
successfully built the project!

## Setting up your IDE

You can use any IDE to work on the project, here are some guides to help you set up your IDE:

- [IntelliJ](https://checkstyle.org/idea.html)
- [Eclipse](https://checkstyle.org/eclipse.html)
- [Netbeans](https://checkstyle.org/netbeans.html)

Using an IDE is not required, but it is recommended.

## Selecting an issue

- Browse through issues with the
   https://github.com/checkstyle/checkstyle/labels/good%20first%20issue label.
- Read the issue description and comments to understand the problem.
- Look at some previous pull requests to help to understand what you will need to do.
- Make a comment on the issue to let others know that you are working on it.

## Working on the issue

For this section of the guide, we will assume that you are working on an issue with the
number `1234`.

Create a new branch for your work using the following command:

```bash
git checkout -b issue-1234
```

Make your changes to the code as described in the issue.
Commit your changes using the following command:

```bash
git add .
git commit -m "Issue #1234: Fixing the issue"
```

Run `mvn clean verify` to ensure that your changes have not broken the build and that all tests
are still passing. If the build does not succeed, carefully read the error messages and try to
fix the issue. If you are unable to fix the issue, reach out in our
[Contributors Chat](https://app.element.io/#/room/#checkstyle_checkstyle:gitter.im) or in the
[Google Group Forum](https://groups.google.com/g/checkstyle-devel) for help.

Push your changes to your forked repository using the following command:

```bash
git push origin issue-1234
```

At this point, you have made your changes and pushed them to your forked repository. Now, you
need to create a pull request.

## Creating a pull request

- Navigate to https://github.com/checkstyle/checkstyle/pulls
- You will see a "Compare & pull request" button. Click it.
- Please read the pull request template and fill in the details as requested.
- Click the "Create pull request" button.
- Check back in about an hour to check if your pull request has passed our automated checks
  and builds. If it has not, you will need to make the necessary changes and push them to your
  forked repository.

DO NOT:

- open a pull request without an attached issue
- open and close pull requests to trigger checks
- open and close pull requests due to struggles with `git`

## Reviewing your pull request

You should be the first person to review your pull request! Make sure to leave any comments on
things you might not understand, need help with, or just want to call out. Your pull request
will be reviewed by the maintainers of the project. They will look at your code
and provide feedback. You may need to make changes to your code based on the feedback.

## Making changes to your pull request

This is an area that trips a lot of people up. There is a lot of ways to make changes to your pull
request, but we will discuss what we believe is the easiest way.

Make the changes to your code on your local machine.
Commit your changes using the following command:

```bash
git add .
git commit -m "Issue #1234: Fixing the issue"
```

We typically require a single commit for each pull request. This
(and lots of other `git` operations) can be done via interactive rebase. You can read more about
this concept at https://git-scm.com/book/en/v2/Git-Tools-Rewriting-History. Here is an example
of what you will need to do:

```bash
git rebase -i HEAD~3
```

`3` is the number of commits that you want to deal with in the interactive rebase. This will open
up a text editor with a list of commits. You will need to change the word `pick` to `fixup` for all
the commits that you want to squash into your first commit.

Here is an example of what the file will look like when it opens:

```bash
pick 1a2b3c4d Issue #4242: Some other issue
pick 5e6f7g8h Issue #1234: Fixing the issue
pick 9i0j1k2l Issue #1234: Fixing the issue

# Rebase a25806399..9i0j1k2l onto 9i0j1k2l (3 commands)
#
# Commands:
# p, pick <commit> = use commit
# r, reword <commit> = use commit, but edit the commit message
# e, edit <commit> = use commit, but stop for amending
# s, squash <commit> = use commit, but meld into previous commit
# f, fixup [-C | -c] <commit> = like "squash" but keep only the previous
#                    commit's log message, unless -C is used, in which case
#                    keep only this commit's message; -c is same as -C but
#                    opens the editor
```

You want to change the word `pick` to `fixup` for the commits that you want to squash into the
first commit. Here is what the file will look like after you have made the changes:

```bash
pick 1a2b3c4d Issue #4242: Some other issue
pick 5e6f7g8h Issue #1234: Fixing the issue
fixup 9i0j1k2l Issue #1234: Fixing the issue

# Rebase a25806399..9i0j1k2l onto 9i0j1k2l (3 commands)
#
# Commands:
# p, pick <commit> = use commit
# r, reword <commit> = use commit, but edit the commit message
# e, edit <commit> = use commit, but stop for amending
# s, squash <commit> = use commit, but meld into previous commit
# f, fixup [-C | -c] <commit> = like "squash" but keep only the previous
#                    commit's log message, unless -C is used, in which case
#                    keep only this commit's message; -c is same as -C but
#                    opens the editor
```

Now, save the file and close the text editor. This will squash the commits into one commit.

Run `mvn clean verify` on your local before pushing to make sure that your changes have not
broken the build and that all tests are still passing, as above.

Push your changes to your forked repository using the following command:

```bash
git push origin issue-1234 --force
```

Note that you MUST force push your changes to your forked repository. This is because you have
rewritten your commit history and you need to force push to overwrite the commit history on your
forked repository.

## Rebasing on the latest changes in the main repository

If your pull request has been open for a while, you may need to rebase your changes on the latest
changes in the main repository. This is to ensure that your changes can be merged into the main
repository without any conflicts.

Fetch the latest changes from the main repository using the following command:

```bash
git checkout main
git pull upstream main
```

You can optionally push the latest changes to the main branch of your forked repository:

```bash
git push origin main
```

Rebase your changes on the latest changes in the main repository using the following command:

```bash
git checkout issue-1234
git rebase main
```

Push your changes to your forked repository using the following command:

```bash
git push origin issue-1234 --force
```

## Dealing with merge conflicts

If you have merge conflicts when you rebase your changes on the latest changes in the main
repository, you will need to resolve the conflicts. This can be a bit tricky, but here is a
guide to help you through it:

- Run `git status` to see which files have conflicts.
- Open the files with conflicts in your IDE or text editor.
- Look for the `<<<<<<<`, `=======`, and `>>>>>>>` markers. These markers show you where the
  conflicts are.
- Resolve the conflicts by editing the files. You will need to remove the `<<<<<<<`, `=======`, and
  `>>>>>>>` markers and make the necessary changes to the code.
- Run `git add .` to stage the changes.
- Run `git rebase --continue` to continue the rebase process.
- Run `mvn clean verify` to ensure that your changes have not broken the build and that all tests
  are still passing.
- Run `git push origin issue-1234 --force` to push your changes to your forked repository.
