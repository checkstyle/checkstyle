/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TrailingComment"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.trailingcomment;

// xdoc section - start
public class Example1 {
  int a;
  int b;
  int c;
  int d; // ok, SUPPRESS CHECKSTYLE
  // violation above 'Don't use trailing comments.'

  public static void main(String[] args) {
    int x = 10;

    if (/* OK */ x > 5) {}
    int a = 5; // trailing comment
    // violation above 'Don't use trailing comments.'
    doSomething(
            "param1"
    ); // trailing comment
    // ok above, trailing comment after ');' is allowed by default

  }

  private static void doSomething(String param) {
  }
}
// xdoc section - end
