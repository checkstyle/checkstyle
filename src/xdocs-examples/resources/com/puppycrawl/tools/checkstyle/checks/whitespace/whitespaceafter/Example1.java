/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="WhitespaceAfter"/>
  </module>
</module>


*/
// Java17
package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

import java.io.InputStream;

// xdoc section -- start
class Example1 {
  int a = 0; int b = 1;
  int c = 2;int d = 3; // violation 'not followed by whitespace'

  void example() throws Exception {
    if (true) {} else if(false) {} // violation 'not followed by whitespace'

    test("a", "b");
    test("a","b"); // violation 'not followed by whitespace'

    for (int i = 0; i < 1; i++) {}
    for(int i = 0; i < 1; i++) {} // violation 'not followed by whitespace'

    try (InputStream ignored = System.in) {}
    try(InputStream ignored = System.in) {} // violation 'not followed by whitespace'

    try {} catch (Exception e) {}
    try {} catch(Exception e) {} // violation 'not followed by whitespace'

    yieldExample();
  }

  String test(String a, String b) {
    return(a + b); // violation 'not followed by whitespace'
  }

  void yieldExample() {
    int x = switch ("a") {
      case "a" -> { yield(1); } // violation ''yield' is not followed by whitespace'
      default -> { yield 2; }
    };
  }
}
// xdoc section -- end
