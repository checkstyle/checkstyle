/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TrailingComment">
      <property name="legalComment" value="^ (TODO|FIXME|trailingcomment).*"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.trailingcomment;

// xdoc section -- start
public class Example5 {
  public static void main(String[] args) {
    int x = 10;

    if (/* OK */ x > 5) {}
    int a = 5; // TODO: refactor this - ok, matches legalComment pattern
    doSomething(
            "param1"
    ); // FIXME: handle edge cases - ok, matches legalComment pattern

    int b = 10; // trailingcomment - ok, matches legalComment pattern
    int c = 15; // violation, regular comment doesn't match pattern
  }

  private static void doSomething(String param) {
  }
}
// xdoc section -- end
