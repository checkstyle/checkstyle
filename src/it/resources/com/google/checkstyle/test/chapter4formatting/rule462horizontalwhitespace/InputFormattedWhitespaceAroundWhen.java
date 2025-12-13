// Java21

package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

class InputFormattedWhitespaceAroundWhen {

  /** Method. */
  void test(Object o) {
    switch (o) {
      case Integer i when (i == 0) -> {}
      case String s when (s.equals("a")) -> {}
      case Point(int x, int y) when !(x >= 0 && y >= 0) -> {}
      default -> {}
    }

    switch (o) {
      case Point(int x, int y) when (x < 9 && y >= 0) -> {}
      case Point(int x, int y) when (x >= 0 && y >= 0) -> {}
      case Point(int x, int y) when !(x >= 0 && y >= 0) -> {}
      default -> {}
    }
  }

  /** Method. */
  void test2(Object o) {

    switch (o) {
      case Integer i when (i == 0) -> {}
      case String s when (s.equals("a")) -> {}
      case Point(int x, int y) when (x >= 0 && y >= 0) -> {}
      default -> {}
    }

    switch (o) {
      case Integer i when i == 0 -> {}
      case String s when s.equals("a") -> {}
      case Point(int x, int y) when x >= 0 && y >= 0 -> {}
      default -> {}
    }
  }

  void emptySwitchStatements() {
    switch ('a') {
      case 'e' -> System.out.println("e");
      case 'i' -> System.out.println("i");
      case 'o' -> System.out.println("o");
      default -> {}
    }
    switch ('a') {
      case 'e' -> System.out.println("e");
      case 'i' -> System.out.println("i");
      case 'o' -> System.out.println("o");
      case 'p' -> {
        // some comment
      }
      default -> {}
    }
    switch ('a') {
      case 'e' -> System.out.println("e");
      case 'i' -> System.out.println("i");
      case 'o' -> System.out.println("o");
      case 'p' -> {
        System.out.println("o");
      }
      default -> {
        System.out.println("default");
      }
    }
  }

  record Point(int x, int y) {}
}
