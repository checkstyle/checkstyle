
# Contributing to Checkstyle

Thank you for your interest in contributing to Checkstyle! Follow the steps below to set up your environment and start contributing.

## 1. Development Setup

### Prerequisites
Ensure you have the following tools installed:
- **Git**
- **Java JDK** (Version >= 11 and <= 17)
- **Maven** (Version >= 3.6.3)

### Prepare the Development Environment
1. Follow the instructions for setting up your environment on [Prepare development environment in Ubuntu](https://checkstyle.org/beginning_development.html).

2. **Fork the repository**:
   - Go to the [Checkstyle GitHub repo](https://github.com/checkstyle/checkstyle).
   - Click the "Fork" button to create a fork of the repository.

3. **Clone your fork**:
   Clone the repository to your local machine using this command:
   ```bash
   git clone git@github.com:your_user_name/checkstyle.git
   ```

4. **Build the project**:
   Run the following command from the project’s root directory to build the project:
   ```bash
   mvn install
   ```

## 2. Import and Debug the Project in Your IDE

Follow these instructions based on your IDE:

- [Eclipse IDE setup](https://checkstyle.org/beginning_development.html#Eclipse_IDE)
- [NetBeans IDE setup](https://checkstyle.org/beginning_development.html#NetBeans_IDE)
- [IntelliJ IDEA setup](https://checkstyle.org/beginning_development.html#IntelliJ_IDEA_IDE)

There are also community video walkthroughs available on [SteLeo1602's YouTube playlist](https://www.youtube.com/playlist?list=PL57WUB2V6ELPpgUUnE2TLKmj4hKZQwXOy).

## 3. Git Workflow

1. **Configure remotes**:
   Add the original Checkstyle repository as a remote:
   ```bash
   git remote add upstream https://github.com/checkstyle/checkstyle
   ```

2. **Create a branch**:
   Create a new branch to work on your changes:
   ```bash
   git checkout -b my-new-check
   ```

3. **Commit your changes**:
   After making changes, stage and commit them:
   ```bash
   git add .
   git commit -m "Description of your changes"
   ```

4. **Push your changes**:
   Push your branch to your fork:
   ```bash
   git push origin my-new-check
   ```

5. **Rebase and resolve conflicts**:
   Before finalizing your changes, make sure your branch is up to date with the main repository:
   ```bash
   git checkout master
   git pull upstream master
   git push origin master
   git checkout my-new-check
   git rebase master
   ```

   If conflicts occur, resolve them:
   ```bash
   git add .
   git rebase --continue
   ```

6. **Push your final changes**:
   After rebasing, force-push your branch:
   ```bash
   git push --force origin my-new-check
   ```

7. **Create a Pull Request**:
   Submit a pull request for review.

## 4. Contribution Guidelines

### Proving Experience
- Begin with [good first issues](https://github.com/checkstyle/checkstyle/labels/good%20first%20issue).
- Progress to issues labeled "easy" and "medium".
- Avoid working on issues without the "approved" label.

### Issue Assignment
- Comment "I am on it" to claim an issue.
- Only the first commenter or assigned individual will be considered the assignee.
- Unapproved fixes for already assigned issues will be rejected.

### Pull Request Limits
- A maximum of **3 active PRs** is allowed.
- You can hold up to **6 issues** if pending PRs exist.

### Communication
- **Preferred**: [Mailing list/Google Group](https://groups.google.com/forum/#!forum/checkstyle-devel).
- **Alternative**: [Gitter](https://gitter.im/checkstyle/checkstyle).

## 5. Writing a GSoC Proposal

If you're applying for GSoC:
1. Write separate proposals for each project.
2. Your proposal should include:
   - Personal information.
   - Project plan (timeline and design).
   - List of Pull Requests you’ve submitted.
   - Your CV and other relevant information.
3. Proven contributions (resolved issues and PRs) are required for serious consideration.

## 6. Additional Resources

- [GitHub Git Cheat Sheet](https://training.github.com/downloads/github-git-cheat-sheet/)
- [Starting Development](https://checkstyle.org/beginning_development.html)
- [Writing Checks](https://checkstyle.org/writingchecks.html)
- [Contributing](https://checkstyle.org/contributing.html)
