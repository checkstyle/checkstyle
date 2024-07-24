//non-compiled with javac: Compilable with Java21

package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

class InputWhitespaceAroundWhen {

  /** method. */
  void test(Object o) {
    switch (o) {
      case Integer i when(i == 0) -> {
      }
      // 2 violations 2 lines above:
      //              ''when' is not followed by whitespace.'
      //              ''when' is not followed by whitespace.'
      case String s when(
              // 2 violations above:
              //              ''when' is not followed by whitespace'
              //              ''when' is not followed by whitespace'
              s.equals("a")) -> {
      }
      case Point(int x, int y) when!(x >= 0 && y >= 0) -> {
      }
      // 2 violations 2 lines above:
      //              ''when' is not followed by whitespace.'
      //              ''when' is not followed by whitespace.'
      default -> {
      }
    }

    switch (o) {
      case Point(int x, int y)when (x < 9 && y >= 0) -> {
      }  // violation above, ''when' is not preceded with whitespace.'
      case Point(int x, int y)when(x >= 0 && y >= 0) -> {
      }
      // 3 violations 2 lines above:
      //              ''when' is not followed by whitespace.'
      //              ''when' is not followed by whitespace.'
      //              ''when' is not preceded with whitespace.'
      case Point(int x, int y)when!(x >= 0 && y >= 0) -> {
      }
      // 3 violations 2 lines above:
      //              ''when' is not followed by whitespace.'
      //              ''when' is not followed by whitespace.'
      //              ''when' is not preceded with whitespace.'
      default -> {
      }
    }
  }

  /** method. */
  void test2(Object o) {

    switch (o) {
      case Integer i when (i == 0) -> {
      }
      case String s when (s.equals("a")) -> {
      }
      case Point(int x, int y) when (x >= 0 && y >= 0) -> {
      }
      default -> {
      }
    }

    switch (o) {
      case Integer i when i == 0 -> {
      }
      case String s when s.equals("a") -> {
      }
      case Point(int x, int y) when x >= 0 && y >= 0 -> {
      }
      default -> {
      }
    }
  }

  record Point(int x, int y) {
  }
}
