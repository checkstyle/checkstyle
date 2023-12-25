/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RightCurly">
      <property name="option" value="alone_or_singleline"/>
      <property name="tokens" value="LITERAL_IF, METHOD_DEF"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

// xdoc section -- start
public class Example4 {

  public void test() {

    boolean foo = false;
    if (foo) {
      bar();
    } else { // violation, ''}' at column 5 should be alone on a line.'
      bar();
    }

    if (foo) {
      bar();
    }
    else {
      bar();
    }

    try {
      bar();
    } catch (Exception e) { // OK because config did not set token LITERAL_TRY
      bar();
    }

  }

  private void bar() {
  }

  public void violate() { bar(); } // OK , because singleline
}
// xdoc section -- end
