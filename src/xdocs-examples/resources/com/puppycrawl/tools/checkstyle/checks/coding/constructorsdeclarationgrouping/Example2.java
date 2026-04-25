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

  // violation 2 lines below """Constructors should be grouped together. The last
  //  grouped constructor is declared at line '18'."""
  Example2(int x) {}

  // violation 2 lines below """Constructors should be grouped together. The last
  //  grouped constructor is declared at line '18'."""
  Example2(String s, int x) {}

  private enum ExampleEnum {

    ONE, TWO, THREE;

    ExampleEnum() {}

    ExampleEnum(int x) {}

    final int x = 10;

    // violation 2 lines below """Constructors should be grouped together.
    //  The last grouped constructor is declared at line '36'."""
    ExampleEnum(String str) {}

    void foo() {}
  }

  // violation 2 lines below """Constructors should be grouped together.
  //  The last grouped constructor is declared at line '18'."""
  Example2(float f) {}
}
// xdoc section -- end
