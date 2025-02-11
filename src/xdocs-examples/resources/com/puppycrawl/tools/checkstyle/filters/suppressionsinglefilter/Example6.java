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

  // filtered violation 'Name 'myConstant' must match pattern'
  private static final int myConstant = 42;

}
// xdoc section -- end
