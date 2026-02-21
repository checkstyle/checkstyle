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
    boolean f = false;
    if (f) {
      bar();
    } // violation, 'should be on the same line'
    else {
      bar();
    }
    if (f) {
      bar();
    } else { // ok, 'should be alone on a line.'
      bar();
    }
    if (f) { bar(); } int i = 0; // violation, 'should be alone on a line.'
    try {
      bar();
    } // violation, 'should be on the same line'
    catch (Exception e) {
      bar();
    }
  }
  private void bar() {
  }
  public void testSingleLine() { bar(); } // ok, 'should be alone on a line.'
  public void violate() { Object b = "b"; } // ok, 'should be alone on a line.'
  public void method0() {
    int mode = 0;
    int x;
    switch (mode) {
      case 1: int y = 1; break;
      case 2: {x = 1;} // ok, 'should be alone on a line.'
      case 3: int z = 0; {break;}
      default: x = 0;
    }
    switch (mode) {
      case 1: x = 1; break;
      default: x = 0; } // ok, 'should be alone on a line.'
  }
}
// xdoc section -- end
