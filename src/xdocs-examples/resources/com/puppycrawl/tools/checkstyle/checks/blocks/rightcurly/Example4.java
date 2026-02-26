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
    boolean f = false;
    if (f) {
      bar();
    }
    else {
      bar();
    }
    if (f) {
      bar();
    } else { // violation, 'should be alone on a line.'
      bar();
    }
    if (f) { bar(); } int i = 0; // violation, 'should be alone on a line.'
    try {
      bar();
    }
    catch (Exception e) {
      bar();
    }
  }
  private void bar() {
  }
  public void testSingleLine() { bar(); }
  public void violate() { Object b = "b"; }
  public void method0() {
    int mode = 0;
    int x;
    switch (mode) {
      case 1: int y = 1; break;
      case 2: {x = 1;}
      case 3: int z = 0; {break;}
      default: {x = 0;}
    }
    switch (mode) {
      case 1: x = 1; break;
      default: x = 0; }
  }
}
// xdoc section -- end
