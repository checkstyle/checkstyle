# Checkstyle Issue 21552 Technical Report

## 1. Problem Description

Issue 21552 describes a defect in comment validation.
The check configuration file contains two XPath rules:
- `violationBetweenAnnotationAndMethod`
- `violationBetweenJavadocAndMethod`

These rules check comments in test input files.
The rules search for comments placed between annotations and target declarations.
The rules also search for comments placed between Javadoc blocks and target declarations.
Such comments cause test side effects.
The rules must catch these misplaced comments.
The current XPath queries only examine children of the `MODIFIERS` node.
When a Java declaration has no access modifier keyword, the query misses the comment.
Thus, package-private methods, default constructors, top-level classes, and module declarations pass without errors.

## 2. Technical Root Cause Analysis

We analyzed the Checkstyle Abstract Syntax Tree with `Main -T`.
JavaParser attaches comments from the token hidden channel.
The parser attaches each comment as a previous sibling to the next token.

When a declaration contains an access modifier keyword like `public`, the comment attaches inside `MODIFIERS`.
In this condition, the comment is a child of `MODIFIERS`.

When a declaration has no access modifier keyword, the comment attaches to a different node:
- For a method without modifiers, the comment attaches inside the `TYPE` node.
- For a constructor without modifiers, the comment attaches directly to `CTOR_DEF`.
- For a class declaration without modifiers, the comment attaches directly to `CLASS_DEF`.
- For a record declaration without modifiers, the comment attaches directly to `RECORD_DEF`.
- For a module declaration, annotations attach to `ANNOTATIONS`, and the comment attaches to `MODULE_DEF`.

Because previous queries only inspected `MODIFIERS/SINGLE_LINE_COMMENT`, all these cases were missed.

## 3. Investigation of Solutions

We evaluated three technical solutions.

### Solution A: Regular Expressions

Pull Request 21567 attempted to use `RegexpMultiline`.
The project maintainer rejected regular expressions because they are error prone and difficult to maintain.

### Solution B: Dedicated Java Check

A dedicated Java check can check token line numbers directly with `DetailAST.getLineNo()`.
This approach requires extensive framework additions, documentation pages, and metadata files.

### Solution C: Refined MatchXpath Queries

We can refine the existing XPath queries to inspect all valid AST locations.
The refined queries check `MODIFIERS`, `TYPE`, and declaration children.
The queries use `preceding-sibling::*[1]` to match only immediately preceding elements.
This prevents false matches on trailing comments.

## 4. Implementation Details

We updated `config/checkstyle-input-checks.xml`.
The new query for `violationBetweenAnnotationAndMethod` checks:
- Comments in `MODIFIERS` preceded by an annotation.
- Comments in `TYPE` where the preceding `MODIFIERS` node contains an annotation.
- Comments under the declaration preceded by `MODIFIERS` or `ANNOTATIONS` containing an annotation.

The new query for `violationBetweenJavadocAndMethod` checks:
- Comments in `MODIFIERS` preceded by Javadoc.
- Comments in `TYPE` preceded by Javadoc.
- Comments under the declaration preceded by Javadoc.

We also updated `config/checkstyle-input-suppressions.xml`.
We added temporary suppressions for existing input files until issue 19803 resolves them.

## 5. Verification and Results

We executed the updated configuration on all test cases:
- Bare methods with annotations trigger violations correctly.
- Constructors with annotations trigger violations correctly.
- Top-level classes with annotations trigger violations correctly.
- Record declarations with annotations trigger violations correctly.
- Module declarations with annotations trigger violations correctly.
- Comments placed before annotations or Javadoc pass cleanly.
- `CommitValidationTest` passed with zero errors.
- `xml:check-format` passed with zero errors.

## 6. Pull Request and Contribution Compliance

We follow all Checkstyle contribution rules:
- Single commit per pull request.
- Commit message format: `Issue #21552: ...` without trailing period.
- No co-authors or body text in the commit message.
- A draft pull request submitted to the upstream repository.
