/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TrailingComment">
      <property name="legalComment" value="^ ok, SUPPRESS CHECKSTYLE$"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.trailingcomment;

// xdoc section - start
public class Example3 {
  int a; // trailing comment
  // violation above 'Don't use trailing comments.'
  int b;
  int c;
  int d; // ok, SUPPRESS CHECKSTYLE
  // ok above, matches legalComment pattern

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
