// non-compiled with javac: Compilable with Java21

package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

class InputFormattedWhitespaceAroundWhen {

  /** method. */
  void test(Object o) {
    switch (o) {
      case Integer i when (i == 0) -> {}
      // 2 violations above:
      //  ''{' is not followed by whitespace.'
      //  ''}' is not preceded with whitespace.'
      case String s when (s.equals("a")) -> {}
      // 2 violations above:
      //  ''{' is not followed by whitespace.'
      //  ''}' is not preceded with whitespace.'
      case Point(int x, int y) when !(x >= 0 && y >= 0) -> {}
      // 2 violations above:
      //  ''{' is not followed by whitespace.'
      //  ''}' is not preceded with whitespace.'
      default -> {}
      // 2 violations above:
      //  ''{' is not followed by whitespace.'
      //  ''}' is not preceded with whitespace.'
    }

    switch (o) {
      case Point(int x, int y) when (x < 9 && y >= 0) -> {}
      // 2 violations above:
      //  ''{' is not followed by whitespace.'
      //  ''}' is not preceded with whitespace.'
      case Point(int x, int y) when (x >= 0 && y >= 0) -> {}
      // 2 violations above:
      //  ''{' is not followed by whitespace.'
      //  ''}' is not preceded with whitespace.'
      case Point(int x, int y) when !(x >= 0 && y >= 0) -> {}
      // 2 violations above:
      //  ''{' is not followed by whitespace.'
      //  ''}' is not preceded with whitespace.'
      default -> {}
      // 2 violations above:
      //  ''{' is not followed by whitespace.'
      //  ''}' is not preceded with whitespace.'
    }
  }

  /** method. */
  void test2(Object o) {

    switch (o) {
      case Integer i when (i == 0) -> {}
      // 2 violations above:
      //  ''{' is not followed by whitespace.'
      //  ''}' is not preceded with whitespace.'
      case String s when (s.equals("a")) -> {}
      // 2 violations above:
      //  ''{' is not followed by whitespace.'
      //  ''}' is not preceded with whitespace.'
      case Point(int x, int y) when (x >= 0 && y >= 0) -> {}
      // 2 violations above:
      //  ''{' is not followed by whitespace.'
      //  ''}' is not preceded with whitespace.'
      default -> {}
      // 2 violations above:
      //  ''{' is not followed by whitespace.'
      //  ''}' is not preceded with whitespace.'
    }

    switch (o) {
      case Integer i when i == 0 -> {}
      // 2 violations above:
      //  ''{' is not followed by whitespace.'
      //  ''}' is not preceded with whitespace.'
      case String s when s.equals("a") -> {}
      // 2 violations above:
      //  ''{' is not followed by whitespace.'
      //  ''}' is not preceded with whitespace.'
      case Point(int x, int y) when x >= 0 && y >= 0 -> {}
      // 2 violations above:
      //  ''{' is not followed by whitespace.'
      //  ''}' is not preceded with whitespace.'
      default -> {}
      // 2 violations above:
      //  ''{' is not followed by whitespace.'
      //  ''}' is not preceded with whitespace.'
    }
  }

  record Point(int x, int y) {}
}
