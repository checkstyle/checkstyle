/*xml
<module name="Checker">
  <module name="SuppressionSingleFilter">
    <property name="files" value="[/\\]target[/\\]"/>
    <property name="checks" value=".*"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;
// xdoc section -- start
public class Example4 {
  // Violation below, 'Method name should be in camelCase (normally flagged)'
  public void METHOD_IN_UPPERCASE() {
    System.out.println("This method name violates naming conventions.");
  }

  // Violation below, 'No braces for single-line if statement (normally flagged)'
  public void checkNumber(int value) {
    if (value > 0) System.out.println("Positive number");
  }
}
// xdoc section -- end
