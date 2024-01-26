/*
WhitespaceAfter
tokens = LITERAL_YIELD


*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

public class InputWhitespaceAfterLiteralYield {
  public static void main(String[] args) {
    int a = switch (args[0]) {
      case "got":
        yield (1);
      case "my":
        yield(3); // violation ''yield' is not followed by whitespace'
      default:
        yield 2;
    };
  }
}
