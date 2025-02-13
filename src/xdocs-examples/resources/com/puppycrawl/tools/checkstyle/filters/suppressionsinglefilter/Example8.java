/*xml
<module name="Checker">
  <module name="SuppressionSingleFilter">
    <property name="files" value="Example8.java"/>
    <property name="checks" value="ParameterNumber"/>
  </module>
  <module name="TreeWalker">
    <module name="ParameterNumber">
      <property name="max" value="5"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;
// xdoc section -- start
public class Example8 {

  // filtered violation 'more than 5 parameters'
  public void exampleMethod(
    int param1, int param2, int param3, int param4,
    int param5
  ) {
    System.out.println("Too many parameters in this method");
  }
}
// xdoc section -- end
