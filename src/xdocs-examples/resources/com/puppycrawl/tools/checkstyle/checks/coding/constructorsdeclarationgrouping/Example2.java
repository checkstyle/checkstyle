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

  Example2(int x) {} // violation 'Constructors should be grouped together'

  Example2(String s, int x) {} // violation 'Constructors should be grouped together'

  private enum ExampleEnum {

    ONE, TWO, THREE;

    ExampleEnum() {}

    ExampleEnum(int x) {}

    final int x = 10;

    ExampleEnum(String str) {} // violation 'Constructors should be grouped together'

    void foo() {}
  }

  Example2(float f) {} // violation 'Constructors should be grouped together'
}
// xdoc section -- end
