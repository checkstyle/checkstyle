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
    } else { bar(); } // violation, ''}' at column 21 should be alone on a line.'

    if (foo) {
      bar();
    } else {
      bar();
    }

    try {
      bar();
    } catch (Exception e) { // OK because config is set to token*
      bar();
    }

  }

  private void bar() {
  }

  public void violate() { Object bar = "bar"; } // violation, ''}' at column 47*'

  public void ok() {
    bar();
  }
}
// xdoc section -- end
