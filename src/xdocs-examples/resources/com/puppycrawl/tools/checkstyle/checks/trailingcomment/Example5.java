/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TrailingComment">
      <property name="legalComment" value="^// (TODO|FIXME|NOSONAR)"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.trailingcomment;

// xdoc section -- start
public class Example5 {
    public static void main(String[] args) {
        int x = 10;

        if (x > 5) {
        }
        int a = 5; // TODO: fix this logic later - ok, matches legalComment pattern
        int b = 6; // FIXME: this should be calculated - ok, matches legalComment pattern
        int c = 7; // NOSONAR - ok, matches legalComment pattern
        int d = 8; // violation, doesn't match legalComment pattern
        doSomething(
                "param1"); // ok, by default such trailing of method/code-block ending is allowed

    }

    private static void doSomething(String param) {
    }
}
// xdoc section -- end