/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ConstructorsDeclarationGrouping"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.constructorsdeclarationgrouping;

// xdoc section -- start
public class Example1 {
  Example1() {}

  Example1(int a){}

  void foo2() {}

  Example1(String str) {} // violation

  Example1(double d) {}

  private enum ExampleEnum {
    ONE, TWO, THREE;

    ExampleEnum() {}

    ExampleEnum(String str) {}

    final int x = 10;

    ExampleEnum(int age) {} // violation

    void foo() {}
  }

  Example1(float f) {} // violation
}
// xdoc section -- end
