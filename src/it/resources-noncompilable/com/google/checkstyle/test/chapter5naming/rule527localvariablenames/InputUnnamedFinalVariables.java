// non-compiled with javac: Compilable with Java25

package com.google.checkstyle.test.chapter5naming.rule527localvariablenames;

/** Some javadoc. */
public class InputUnnamedFinalVariables {

  /** Some javadoc. */
  void foo() {
    final var _ = 1;
    final int[] arr = {1, 2, 3};

    int count = 0;
    for (final int _ : arr) {
      count++;
    }

    try {
      // something
    } catch (final Error _) {
      // error handling
    }

    // violation below 'Local final variable name '_name' must match pattern'
    final var _name = "test";
    // violation below 'Local final variable name 'na_me' must match pattern'
    final var na_me = "test";
    // violation below 'Local final variable name 'name_' must match pattern'
    final var name_ = "test";
  }
}
