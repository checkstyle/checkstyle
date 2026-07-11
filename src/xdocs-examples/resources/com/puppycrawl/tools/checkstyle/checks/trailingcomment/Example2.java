/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TrailingComment">
      <property name="format" value="^\s*$"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.trailingcomment;

// xdoc section -- start
public class Example2 {
  int a; // violation 'Don't use trailing comments.'
  int b; // violation 'Don't use trailing comments.'
  int c; // violation 'Don't use trailing comments.'
  int d; // violation 'Don't use trailing comments.'

  public static void main(String[] args) {
    int x = 10;

    if (/* OK, this comment does not end the line */ x > 5) {}
    int a = 5; // violation 'Don't use trailing comments.'
    doSomething(
            "param1"
    ); // violation 'Don't use trailing comments.'

  }

  private static void doSomething(String param) {
  }
}
// xdoc section -- end
