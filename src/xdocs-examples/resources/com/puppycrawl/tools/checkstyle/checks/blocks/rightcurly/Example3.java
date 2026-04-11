/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RightCurly">
      <property name="option" value="alone"/>
      <property name="tokens" value="LITERAL_SWITCH, LITERAL_CASE"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

// xdoc section -- start
public class Example3 {
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
    } else {
      bar();
    }
    if (f) { bar(); } int i = 0;
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
      case 2: {x = 1;} // violation, 'should be alone on a line.'
      case 3: int z = 0; {break;}
      default: x = 0;
    }
    switch (mode) {
      case 1: x = 1; break;
      default: x = 0; } // violation, 'should be alone on a line.'
  }
}
// xdoc section -- end
