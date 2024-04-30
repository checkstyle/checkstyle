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

  int x;

  Example1() {}

  Example1(String s) {}

  // comments between constructors are allowed.
  Example1(int x) {}

  Example1(String s, int x) {}

  void foo() {}

  private enum ExampleEnum {

    ONE, TWO, THREE;

    ExampleEnum() {}

    ExampleEnum(int x) {}

    ExampleEnum(String s) {}

    int x = 10;

    void foo() {}
  }
}
// xdoc section -- end
