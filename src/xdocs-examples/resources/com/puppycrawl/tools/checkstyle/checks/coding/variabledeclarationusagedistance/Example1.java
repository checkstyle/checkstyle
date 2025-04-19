/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="VariableDeclarationUsageDistance"/>
  </module>
</module>
*/


package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

// xdoc section -- start
public class Example1 {

  public void foo1() {
    // violation below, 'variable 'num' declaration and its first usage is 4.'
    int num;
    final double PI;   // OK, final variables not checked
    System.out.println("Statement 1");
    System.out.println("Statement 2");
    System.out.println("Statement 3");
    num = 1;
    PI = 3.14;
  }

  public void foo2() {
    int a;          // OK, used in different scope
    int b;          // OK, used in different scope
    int count = 0;  // OK, used in different scope

    {
      System.out.println("Inside inner scope");
      a = 1;
      b = 2;
      count++;
    }
  }
}
// xdoc section -- end
