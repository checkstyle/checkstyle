// Java21

package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

class InputWhitespaceAroundWhen {

  /** Method. */
  void test(Object o) {
    switch (o) {
      case Integer i when(i == 0) -> // violation ''when' is not followed by whitespace.'
        {
        }
      // violation below, ''when' is not followed by whitespace.'
      case String s when(
              s.equals("a")) ->
        {
        }

      // violation below, ''when' is not followed by whitespace.'
      case Point(int x, int y) when!(x >= 0 && y >= 0) ->
        {
        }

      default ->
        {
        }
    }

    switch (o) {
      case Point(int x, int y)when (x < 9 && y >= 0) ->
        {
        }  // violation 2 lines above ''when' is not preceded with whitespace.'
      case Point(int x, int y)when(x >= 0 && y >= 0) ->
        {
        }
      // 2 violations 3 lines above:
      //              ''when' is not followed by whitespace.'
      //              ''when' is not preceded with whitespace.'
      case Point(int x, int y)when!(x >= 0 && y >= 0) ->
        {
        }
      // 2 violations 3 lines above:
      //              ''when' is not followed by whitespace.'
      //              ''when' is not preceded with whitespace.'
      default ->
        {
        }
    }
  }

  /** Method. */
  void test2(Object o) {

    switch (o) {
      case Integer i when (i == 0) ->
        {
        }
      case String s when (s.equals("a")) ->
        {
        }
      case Point(int x, int y) when (x >= 0 && y >= 0) ->
        {
        }
      default ->
        {
        }
    }

    switch (o) {
      case Integer i when i == 0 ->
        {
        }
      case String s when s.equals("a") ->
        {
        }
      case Point(int x, int y) when x >= 0 && y >= 0 ->
        {
        }
      default ->
        {
        }
    }
  }

  void emptySwitchStatements() {
    switch ('a') {
      case 'e' -> System.out.println("e");
      case 'i' -> System.out.println("i");
      case 'o' -> System.out.println("o");
      default -> {
        // some comment
      }
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
      default ->
        {
          System.out.println("default");
        }
    }
  }

  record Point(int x, int y) {
  }
}
