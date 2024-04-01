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

    void foo() {}

    Example1() {}

    Example1(int a){}

    void foo2() {}

    Example1(String str) {} // violation
}
// xdoc section -- end
