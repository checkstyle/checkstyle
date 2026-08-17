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
  int a;
  int b;
  int c;
  int d; // ok, SUPPRESS CHECKSTYLE

  public static void main(String[] args) {
    int x = 10;

    if (/* OK */ x > 5) {}
    int a = 5; // violation 'Don't use trailing comments.'
    doSomething(
            "param1"
    ); // ok, by default such trailing of method/code-block ending is allowed

  }

  private static void doSomething(String param) {
  }
}
// xdoc section - end
