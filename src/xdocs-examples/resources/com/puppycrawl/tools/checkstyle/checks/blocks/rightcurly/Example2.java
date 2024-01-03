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
    } else { bar(); }
    // violation above, ''}' at column 21 should be alone on a line.'

    if (foo) {
        bar();
    } else {
        bar();
    }

    try {
      bar();
    } catch (Exception e) { // OK because config is
      // set to token METHOD_DEF and LITERAL_ELSE
      bar();
    }

  }

  private void bar() {
  }

  public void violate() { Object bar = "bar"; }
  // violation above, ''}' at column 47 should be alone on a line.'

  public void ok() {
        bar();
    }
}
// xdoc section -- end
