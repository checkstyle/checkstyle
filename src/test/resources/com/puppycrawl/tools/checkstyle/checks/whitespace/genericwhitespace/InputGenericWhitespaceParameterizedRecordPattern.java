/*
GenericWhitespace

*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.genericwhitespace;

/** Tests for issue #20505: GenericWhitespace false positive for parameterized record pattern. */
public class InputGenericWhitespaceParameterizedRecordPattern {

  record Pair<T>(T left, T right) {}

  static class Outer {
    record Pair<T>(T left, T right) {}
  }

  void test(Object obj) {
    // ok - parameterized record pattern without whitespace after '>'
    if (obj instanceof Pair<?>(var left, var right)) {
      left.toString();
    }

    if (obj instanceof Pair<?> // violation '>' is followed by whitespace.
        (var left, var right)) {
      left.toString();
    }

    // ok - qualified name without space (DOT in AST, must not be flagged)
    if (obj instanceof Outer.Pair<?>(var left, var right)) {
      left.toString();
    }

    // qualified name with space after '>'
    if (obj instanceof Outer.Pair<?> // violation '>' is followed by whitespace.
        (var left, var right)) {
      left.toString();
    }
  }
}
