/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ConstructorsDeclarationGrouping"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.constructorsdeclarationgrouping;

// xdoc section -- start
public class Example2 {

  int x;

  Example2() {}

  Example2(String s){}

  void foo() {}

  Example2(int x) {} // violation

  Example2(String s, int x) {} // violation

  private enum ExampleEnum {

    ONE, TWO, THREE;

    ExampleEnum() {}

    ExampleEnum(int x) {}

    final int x = 10;

    ExampleEnum(String str) {} // violation

    void foo() {}
  }

  Example2(float f) {} // violation
}
// xdoc section -- end
