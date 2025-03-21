/*xml
<module name="Checker">
  <module name="MemberName"/>
  <module name="MethodName"/>
  <module name="SuppressionSingleFilter">
    <property name="files" value="Example7.java"/>
    <property name="checks" value="MemberName"/>
  </module>
  <module name="SuppressionSingleFilter">
    <property name="files" value="Example7.java"/>
    <property name="checks" value="MethodName"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;
// xdoc section -- start
public class Example7 {
  // filtered violation below 'Name 'MyVariable' must match pattern'
  private int MyVariable = 5;
  // filtered violation below 'Name 'PrintHello' must match pattern'
  public void PrintHello() {
  }

  public void printHello() {
  }
}
// xdoc section -- end
