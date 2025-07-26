/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionXpathFilter">
      <property name="file" value="${config.folder}/suppressions5.xml"/>
    </module>
    <module name="CyclomaticComplexity">
      <property name="max" value="3"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppressionxpathfilter;

// xdoc section -- start
public class Example5 {

  int a, b, c, d, e, n;

  public void setSomeVar() { // filtered violation 'Cyclomatic Complexity is 4'
    if (a == b) {
      int x = 5;
    }
    else if (a == 0 && b == c) {
      System.out.println("*Silence*");
    }
  }

  public void testMethod() { // violation, 'Cyclomatic Complexity is 5'
    while (a < b && a > c) {
      fun1();
    }
    if (a == b) {
      do {
        fun1();
      }
      while (d==a);
    }
  }

  public void fun1() {}

}
// xdoc section -- end
