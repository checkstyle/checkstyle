/*
WhitespaceAfter
tokens = COMMA, SEMI


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

// xdoc section -- start
class Example2 {
  void example() {
    int a = 0; int b = 1;
    int c = 2;int d = 3; // violation 'not followed by whitespace'

    testMethod(a, b);
    testMethod(c,d); // violation 'not followed by whitespace'

    for(;;) {}
  }
  void testMethod(int a, int b) { }
}
// xdoc section -- end
