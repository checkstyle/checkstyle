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

  // filterd violation 'Name 'MyVariable' must match pattern'
  private int MyVariable = 5;

  // filtered violation 'Name 'PrintHello' must match pattern'
  public void PrintHello() {
    System.out.println("Hello");
  }

  public void printHello() {
    System.out.println("Hello");
  }
}
// xdoc section -- end
