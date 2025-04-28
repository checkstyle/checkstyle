/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TrailingComment"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.trailingcomment;

// xdoc section -- start
public class Example1 {
  public static void main(String[] args) {
    int x = 10;

    if (/* OK */ x > 5) {}
    int a = 5; // violation
    doSomething(
            "param1"
    ); // ok, by default such trailing of method/code-block ending is allowed

  }

  private static void doSomething(String param) {
  }
}
// xdoc section -- end
