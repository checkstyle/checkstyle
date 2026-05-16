/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ConstructorsDeclarationGrouping">
      <property name="orderByIncreasingArity" value="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.constructorsdeclarationgrouping;

// xdoc section -- start
public class Example2 {
  int x;
  Example2() {}

  Example2(String s) {}

  // comments between constructors are allowed.
  Example2(int x, int y, int z) {}

  int a = 0;

  // violation 2 lines below """Constructors should be grouped together.
  // The last grouped constructor is declared at line '21'."""
  Example2(String s, int x) {}
  // violation above 'Constructors should be ordered by increasing arity.'

  private enum ExampleEnum {

    ONE, TWO, THREE;

    ExampleEnum() {}

    void foo() {}

    // violation 2 lines below """Constructors should be grouped together.
    // The last grouped constructor is declared at line '34'."""
    ExampleEnum(int x, int y) {}

    // violation 2 lines below """Constructors should be grouped together.
    // The last grouped constructor is declared at line '34'."""
    ExampleEnum(String s) {}
    // violation above 'Constructors should be ordered by increasing arity.'

  }

  class CorrectInput {
    CorrectInput() {}
    CorrectInput(String s) {}
    CorrectInput(int x) {}
    CorrectInput(String s, int x) {}
  }
}
// xdoc section -- end
