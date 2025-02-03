/*xml
<module name="Checker">
  <module name="SuppressionSingleFilter">
    <property name="files" value="Example5.java"/>
    <property name="checks" value="MethodName"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;
// xdoc section -- start
public class Example5 {
  // filterd violation 'Name 'example_Method' must match pattern'
  public void example_Method() {
    System.out.println("This method name is not in camelCase!");
  }

  // filterd violation Name 'Another_Method' must match pattern
  public void Another_Method() {
    System.out.println("Another non-camelCase method name");
  }
}
// xdoc section -- end
