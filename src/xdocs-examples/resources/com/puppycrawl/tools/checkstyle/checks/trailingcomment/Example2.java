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

import java.util.Random;

// xdoc section -- start
public class Example2 {
  public static void main(String[] args) {
    int x = new Random().nextInt(100);

    if (/* OK, this comment does not end the line */ x > 5) {}
    int a = 5; // violation, line content before comment should match pattern "^\s*$"
    doSomething(
            "param1"
    ); // violation, line content before comment should match pattern "^\s*$"
  }

  private static void doSomething(String param) {
  }
}
// xdoc section -- end
