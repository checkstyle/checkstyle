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
    } // violation, 'should be on the same line'
    // as the next part of a multi-block statement (one that directly
    // contains multiple blocks: if/else-if/else, do/while or try/catch/finally).
    else {
      bar();
    }

    if (foo) {
      bar();
    } else {
      bar();
    }

    if (foo) { bar(); } int i = 0;
    // violation above, 'should be alone on a line.'

    if (foo) { bar(); }
    i = 0;

    try {
      bar();
    } // violation, 'should be on the same line'
    // as the next part of a multi-block statement (one that directly
    // contains multiple blocks: if/else-if/else, do/while or try/catch/finally).
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
