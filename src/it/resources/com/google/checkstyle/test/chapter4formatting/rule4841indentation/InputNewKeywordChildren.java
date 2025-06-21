package com.google.checkstyle.test.chapter4formatting.rule4841indentation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

/** some data. */
public class InputNewKeywordChildren {

  /** some data. */
  public Object foo() {
    return Optional.empty()
        .orElseThrow(
            () ->
// violation below '.* incorrect indentation level 0, expected .* 14, 16.'
new IllegalArgumentException(
"Something wrong 1, something wrong 2, something wrong 3"));
    // violation above '.* incorrect indentation level 0, expected level should be one .* 18, 20'
  }

  /** some data. */
  public Object foo1() {
    return Optional.empty()
        .orElseThrow(
            () ->
                new IllegalArgumentException(
"Something wrong 1, something wrong 2, something wrong 3"));
    // violation above '.* incorrect indentation level 0, expected .* 18, 20.'
  }

  /** some data. */
  public void foo2() throws IOException {
    BufferedReader bf =
        new BufferedReader(
            new InputStreamReader(System.in) {
              int anInt = 0;
            });
  }
}
