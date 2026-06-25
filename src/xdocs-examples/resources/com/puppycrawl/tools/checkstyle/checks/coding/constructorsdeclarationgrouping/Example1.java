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

  Example1(String s, int x, int y) {}

  // comments between constructors are allowed.
  Example1(int x) {}

  int a = 0;

  // violation 2 lines below """Constructors should be grouped together.
  // The last grouped constructor is declared at line '21'."""
  Example1(String s, int x) {}

  private enum ExampleEnum {

    ONE, TWO, THREE;

    ExampleEnum() {}

    void foo() {}

    // violation 2 lines below """Constructors should be grouped together.
    // The last grouped constructor is declared at line '33'."""
    ExampleEnum(int x, int y) {}

    // violation 2 lines below """Constructors should be grouped together.
    // The last grouped constructor is declared at line '33'."""
    ExampleEnum(String s, int x) {}

  }

  class InputWithOrderedCtors {
    InputWithOrderedCtors() {}
    InputWithOrderedCtors(String s) {}
    InputWithOrderedCtors(int x) {}
    InputWithOrderedCtors(String s, int x) {}
  }
}
// xdoc section -- end
