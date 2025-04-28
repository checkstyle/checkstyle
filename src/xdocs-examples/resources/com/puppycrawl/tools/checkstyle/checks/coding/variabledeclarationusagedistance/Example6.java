/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="VariableDeclarationUsageDistance">
      <property name="ignoreFinal" value="false"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

// xdoc section -- start
public class Example6 {

  public void foo1() {
    // violation below, 'variable 'num' declaration and its first usage is 4.'
    int num;
    // violation below, 'variable 'PI' declaration and its first usage is 5.'
    final double PI;
    System.out.println("Statement 1");
    System.out.println("Statement 2");
    System.out.println("Statement 3");
    num = 1;
    PI = 3.14;
  }

  public void foo2() {
    int a;          // ok, used in different scope
    int b;          // ok, used in different scope
    int count = 0;  // ok, used in different scope

    {
      System.out.println("Inside inner scope");
      a = 1;
      b = 2;
      count++;
    }
  }
}
// xdoc section -- end
