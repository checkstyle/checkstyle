// Java21

package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

class InputWhitespaceAroundWhen {

  /** Method. */
  void test(Object o) {
    switch (o) {
      // violation 2 lines below ''when' is not followed by whitespace.'
      // violation 2 lines below ''{' at column 9 should be on the previous line.'
      case Integer i when(i == 0) ->
        {
        }
      // violation 2 lines below ''when' is not followed by whitespace.'
      // violation 3 lines below ''{' at column 9 should be on the previous line.'
      case String s when(
              s.equals("a")) ->
        {
        }

      // violation 2 lines below ''when' is not followed by whitespace.'
      // violation 2 lines below ''{' at column 9 should be on the previous line.'
      case Point(int x, int y) when!(x >= 0 && y >= 0) ->
        {
        }
      // violation 2 lines below ''{' at column 9 should be on the previous line.'
      default ->
        {
        }
    }

    switch (o) {
      // violation 2 lines below ''when' is not preceded with whitespace.'
      // violation 2 lines below ''{' at column 9 should be on the previous line.'
      case Point(int x, int y)when (x < 9 && y >= 0) ->
        {
        }
      // 2 violations 4 lines below:
      //              ''when' is not followed by whitespace.'
      //              ''when' is not preceded with whitespace.'
      // violation 2 lines below ''{' at column 9 should be on the previous line.'
      case Point(int x, int y)when(x >= 0 && y >= 0) ->
        {
        }
      // 2 violations 4 lines below:
      //              ''when' is not followed by whitespace.'
      //              ''when' is not preceded with whitespace.'
      // violation 2 lines below ''{' at column 9 should be on the previous line.'
      case Point(int x, int y)when!(x >= 0 && y >= 0) ->
        {
        }
      // violation 2 lines below ''{' at column 9 should be on the previous line.'
      default ->
        {
        }
    }
  }

  /** Method. */
  void test2(Object o) {

    switch (o) {
      // violation 2 lines below ''{' at column 9 should be on the previous line.'
      case Integer i when (i == 0) ->
        {
        }
      // violation 2 lines below ''{' at column 9 should be on the previous line.'
      case String s when (s.equals("a")) ->
        {
        }
      // violation 2 lines below ''{' at column 9 should be on the previous line.'
      case Point(int x, int y) when (x >= 0 && y >= 0) ->
        {
        }
      // violation 2 lines below ''{' at column 9 should be on the previous line.'
      default ->
        {
        }
    }

    switch (o) {
      // violation 2 lines below ''{' at column 9 should be on the previous line.'
      case Integer i when i == 0 ->
        {
        }
      // violation 2 lines below ''{' at column 9 should be on the previous line.'
      case String s when s.equals("a") ->
        {
        }
      // violation 2 lines below ''{' at column 9 should be on the previous line.'
      case Point(int x, int y) when x >= 0 && y >= 0 ->
        {
        }
      // violation 2 lines below ''{' at column 9 should be on the previous line.'
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
      // violation 2 lines below ''{' at column 9 should be on the previous line.'
      default ->
        {
          System.out.println("default");
        }
    }
  }

  record Point(int x, int y) {
  }
}
