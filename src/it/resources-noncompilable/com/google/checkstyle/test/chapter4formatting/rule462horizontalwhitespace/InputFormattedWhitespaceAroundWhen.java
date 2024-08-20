//non-compiled with javac: Compilable with Java21

package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

class InputFormattedWhitespaceAroundWhen {

  /** method. */
  void test(Object o) {
    switch (o) {
      case Integer i when (i == 0) -> {}
      case String s when(
              s.equals("a")) -> {
      }
      case Point(int x, int y) when!(x >= 0 && y >= 0) -> {
      }
      default -> {
      }
    }

    switch (o) {
      case Point(int x, int y)when (x < 9 && y >= 0) -> {
      }
      case Point(int x, int y)when(x >= 0 && y >= 0) -> {
      }
      case Point(int x, int y)when!(x >= 0 && y >= 0) -> {
      }
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
