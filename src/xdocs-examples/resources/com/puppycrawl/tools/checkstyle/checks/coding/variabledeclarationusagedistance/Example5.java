/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="VariableDeclarationUsageDistance">
      <property name="validateBetweenScopes" value="true"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

// xdoc section -- start
public class Example5 {

  public void foo1() {
    // violation below, 'variable 'num' declaration and its first usage is 4.'
    int num;
    final double PI;   // ok, final variables not checked
    System.out.println("Statement 1");
    System.out.println("Statement 2");
    System.out.println("Statement 3");
    num = 1;
    PI = 3.14;
  }

  public void foo2() {
    int a;          // ok, distance = 2
    int b;          // ok, distance = 3
    // violation below, 'variable 'count' declaration and its first usage is 4.'
    int count = 0;

    {
      System.out.println("Inside inner scope");
      a = 1;
      b = 2;
      count++;
    }
  }
}
// xdoc section -- end
