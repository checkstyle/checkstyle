/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RightCurly">
      <property name="option" value="alone_or_singleline"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

// xdoc section -- start
public class Example3 {

  public void test() {

    boolean foo = false;
    if (foo) {
      bar();
    }
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
    }
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

  public void testSingleLine() { bar(); } // ok, because singleline is allowed
}
// xdoc section -- end
