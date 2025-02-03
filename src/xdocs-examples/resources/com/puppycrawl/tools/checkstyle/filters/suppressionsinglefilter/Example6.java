/*xml
<module name="Checker">
  <module name="SuppressionSingleFilter">
    <property name="files" value="Example6.java"/>
    <property name="checks" value="ConstantName"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;
// xdoc section -- start
public class Example6 {

  // Constant name is not in upper case with underscores
  private static final int myConstant = 42;
  // This constant name violates the naming convention

}
// xdoc section -- end
