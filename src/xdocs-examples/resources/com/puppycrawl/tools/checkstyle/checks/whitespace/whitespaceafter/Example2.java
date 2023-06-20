/*
WhitespaceAfter
tokens = COMMA, SEMI


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

class Example2 {
  void example() {
    // xdoc section -- start
    int a = 0; int b = 1;
    int c = 2;int d = 3; // violation 'not followed by whitespace'

    testMethod(a, b);
    testMethod(c,d); // violation 'not followed by whitespace'

    for(;;) {}
    // xdoc section -- end
  }
  void testMethod(int a, int b) { }
}
