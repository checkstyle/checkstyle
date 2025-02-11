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
  public void example_Method() {  // ✅ Fixed indentation (4 spaces)
    System.out.println("This method name is not in camelCase!");
  }

  public void Another_Method() {  // ✅ Fixed indentation (4 spaces)
    System.out.println("Another non-camelCase method name");
  }
}
// xdoc section -- end
