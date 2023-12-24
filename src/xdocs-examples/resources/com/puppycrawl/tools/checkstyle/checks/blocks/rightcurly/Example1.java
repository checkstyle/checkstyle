/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RightCurly"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

// xdoc section -- start
public class Example1 {

  public void test() {

    boolean foo = false;
    if (foo) {
      bar();
    } // violation, ''}' at column 5 should be on the same line*'
    else {
      bar();
    }

    if (foo) {
      bar();
    } else {
      bar();
    }

    if (foo) { bar(); } int i = 0; // violation, ''}' at column 23 should be*'

    if (foo) { bar(); }
    i = 0;

    try {
      bar();
    } // violation, ''}' at column 5 should be on the same line*'
    catch (Exception e) {
      bar();
    }

    try {
      bar();
    } catch (Exception e) {
      bar();
    }

  }

  private void bar() {
  }

  public void testSingleLine() { bar(); } // OK, because singleline is allowed
}
// xdoc section -- end
