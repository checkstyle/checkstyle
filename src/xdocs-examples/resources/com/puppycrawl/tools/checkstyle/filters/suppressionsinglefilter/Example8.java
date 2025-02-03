/*xml
<module name="Checker">
  <module name="SuppressionSingleFilter">
    <property name="files" value="Example8.java"/>
    <property name="checks" value="ParameterNumber"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;
// xdoc section -- start
public class Example8 {

  // filtered violation 'too many parameters'
  public void exampleMethod(
    int param1, int param2, int param3, int param4,
    int param5, int param6, int param7, int param8
  ) {
    System.out.println("Too many parameters in this method");
  }

  public void validMethod(int param1, int param2) {
    System.out.println("This method is valid");
  }

}
// xdoc section -- end
