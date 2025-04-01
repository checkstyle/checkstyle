/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ParameterNumber">
      <property name="max" value="5"/>
    </module>
  </module>
  <module name="SuppressionSingleFilter">
    <property name="files" value="Example8.java"/>
    <property name="checks" value="ParameterNumber"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;
// xdoc section -- start
public class Example8 {
  // filtered violation below 'More than 5 parameters (found 6)'
  public void exampleMethod(
    int param1, int param2, int param3, int param4, int param5, int param6
  ) {}
}
// xdoc section -- end
