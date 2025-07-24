/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionXpathFilter">
      <property name="file" value="suppressions1.xml"/>
      <property name="optional" value="false"/>
    </module>
    <module name="CyclomaticComplexity">
      <property name="max" value="3"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppressionxpathfilter;

// xdoc section -- start
public class Example1 {
  int a, b, c, d, e, n;

  public void sayHelloWorld() { // filtered violation 'Cyclomatic Complexity is 4'
    if (a == b) { // 2, if
      System.out.println("Hello World");
    }
    else if (a == 0 && b == c) {
      System.out.println("*Silence*");
    }
  }

}
// xdoc section -- end
