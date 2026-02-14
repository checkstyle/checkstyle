/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RightCurly">
      <property name="option" value="alone"/>
      <property name="tokens" value="LITERAL_ELSE, METHOD_DEF"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

// xdoc section -- start
public class Example2 {

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
      // ok above because config is set to token METHOD_DEF and LITERAL_ELSE
      bar();
    }

  }

  private void bar() {
  }

  public void testSingleLine() { bar(); } // violation, 'should be alone on a line'
}
// xdoc section -- end