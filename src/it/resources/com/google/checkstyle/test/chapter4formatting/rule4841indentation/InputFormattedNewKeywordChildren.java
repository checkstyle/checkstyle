package com.google.checkstyle.test.chapter4formatting.rule4841indentation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

/** Some data. */
public class InputFormattedNewKeywordChildren {

  static {
    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
      throw new IllegalStateException(
          "Please check if you have proper PostgreSQL JDBC Driver jar on the classpath", e);
    }
  }

  static {
    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
      throw new IllegalStateException(
          "Please check if you have proper PostgreSQL JDBC Driver jar on the classpath", e);
    }
  }

  /** Some data. */
  public Object foo() {
    return Optional.empty()
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    "Something wrong 1, something wrong 2, something wrong 3"));
  }

  /** Some data. */
  public void foo2() throws IOException {
    BufferedReader bf =
        new BufferedReader(
            new InputStreamReader(System.in) {
              int anInt = 0;
            });
  }

  /** Some data. */
  public Object foo4(int data) {
    return Optional.empty()
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    "something wrong 1, something wrong 2, something wrong 3"));
  }

  /** Some data. */
  public Object foo5(int data) {
    return Optional.empty()
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    "something wrong 1, something wrong 2, something wrong 3"));
  }

  /** Some data. */
  public void createExpressionIssue(Object invocation, String toBeRemovedExpression) {
    throw new IllegalArgumentException(
        "The expression "
            + toBeRemovedExpression
            + ", which creates argument of the invocation "
            + invocation
            + " cannot be removed."
            + " Override method `canRemoveExpression` to customize this behavior.");
  }
}
