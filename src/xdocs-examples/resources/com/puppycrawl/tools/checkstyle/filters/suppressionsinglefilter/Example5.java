/*xml
<module name="Checker">
  <module name="MethodName"/>
  <module name="SuppressionSingleFilter">
    <property name="files" value="Example5.java"/>
    <property name="checks" value="MethodName"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;
// xdoc section -- start
public class Example5 {
  // filtered violation below 'Name 'example_Method' must match pattern'
  public void example_Method() {
  }
  // filtered violation below 'Name 'Another_Method' must match pattern'
  public void Another_Method() {
  }
}
// xdoc section -- end
