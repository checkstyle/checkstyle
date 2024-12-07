/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MagicNumber">
      <property name="tokens" value="NUM_DOUBLE, NUM_FLOAT"/>
      <property name="ignoreNumbers" value="0, 0.5, 1"/>
      <property name="ignoreFieldDeclaration" value="true"/>
      <property name="ignoreAnnotation" value="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.magicnumber;

// xdoc section -- start
@Example2.Annotation(6)
public class Example2 {
  private int field = 7;

  void method1() {
    int i = 1;
    int j = 8;
  }
  public void method2() {
    final TestClass testObject = new TestClass(62);
    final int a = 3;
    final int[] b = {4, 5};
    final int c = -3;
    final int d = +4;
    final int e = method3(10, 20);
    final int f = 3 * 4;
    final int g = 3 / 4;
    final int h = 3 + 4;
    final int i = 3 - 4;
    final int j = (int) 3.4;
  }
  private int method3(int a, int b) {
    return a + b;
  }
  public int hashCode() {
    return 10;
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
