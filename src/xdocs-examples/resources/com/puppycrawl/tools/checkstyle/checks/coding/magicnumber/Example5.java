/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MagicNumber">
      <property name="constantWaiverParentToken" value="ASSIGN, ARRAY_INIT, EXPR,
      UNARY_PLUS, UNARY_MINUS, TYPECAST, ELIST, DIV, PLUS"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.magicnumber;

// xdoc section -- start
@Example5.Annotation(6) // violation, ''6' is a magic number.'
public class Example5 {
  private int field = 7; // violation, ''7' is a magic number.'

  void method1() {
    int i = 1;
    int j = 8; // violation, ''8' is a magic number.'
  }
  public void method2() {
    final TestClass testObject = new TestClass(62);
    // violation above, ''62' is a magic number.'
    final int a = 3; // ok as waiver is ASSIGN
    final int[] b = {4, 5}; // ok as waiver is ARRAY_INIT
    final int c = -3; // ok as waiver is UNARY_MINUS
    final int d = +4; // ok as waiver is UNARY_PLUS
    final int e = method3(10, 20);
    // 2 violations above:
    //  ''10' is a magic number.'
    //  ''20' is a magic number.'
    final int f = 3 * 4;
    // 2 violations above:
    //  ''3' is a magic number.'
    //  ''4' is a magic number.'
    final int g = 3 / 4; // ok as waiver is DIV
    final int h = 3 + 4; // ok as waiver is PLUS
    final int i = 3 - 4;
    // 2 violations above:
    //  ''3' is a magic number.'
    //  ''4' is a magic number.'
    final int j = (int) 3.4; // ok as waiver is TYPECAST
  }
  private int method3(int a, int b) {
    return a + b;
  }
  public int hashCode() {
    return 10; // violation, ''10' is a magic number.'
  }
  @interface Annotation {
    int value() default 10;
    int[] value2() default {10};
  }
  class TestClass {
    TestClass(int field) {}
  }
}
// xdoc section -- end
