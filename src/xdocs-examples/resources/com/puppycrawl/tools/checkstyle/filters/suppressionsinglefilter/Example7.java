/*xml
<module name="Checker">
  <module name="SuppressionSingleFilter">
    <property name="files" value="Example7.java"/>
    <property name="checks" value="MemberName"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;
// xdoc section -- start
public class Example7 {

  // Member name should be in camelCase
  private int MyVariable = 5;  // This violates camelCase naming convention

  // Method name does not follow camelCase naming convention
  public void PrintHello() {
    System.out.println("Hello");
  }

  public void printHello() {
    System.out.println("Hello");
  }
}
// xdoc section -- end
