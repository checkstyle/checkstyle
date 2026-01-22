package com.google.checkstyle.test.chapter4formatting.rule4841indentation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

/** Some data. */
public class InputNewKeywordChildrenCorrect {

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
}
