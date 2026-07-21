/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="WhitespaceAfter">
      <property name="tokens" value="COMMA, SEMI"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

import java.io.InputStream;

// xdoc section -- start
class Example2 {
  int a = 0; int b = 1;
  int c = 2;int d = 3; // violation 'not followed by whitespace'

  void example() throws Exception {
    if (true) {} else if(false) {}

    test("a", "b");
    test("a","b"); // violation 'not followed by whitespace'

    for (int i = 0; i < 1; i++) {}
    for(int i = 0; i < 1; i++) {}

    try (InputStream ignored = System.in) {}
    try(InputStream ignored = System.in) {}

    try {} catch (Exception e) {}
    try {} catch(Exception e) {}

    yieldExample();
  }

  String test(String a, String b) {
    return(a + b);
  }

  void yieldExample() {
    int x = switch ("a") {
      case "a" -> { yield(1); }
      default -> { yield 2; }
    };
  }
}
// xdoc section -- end
