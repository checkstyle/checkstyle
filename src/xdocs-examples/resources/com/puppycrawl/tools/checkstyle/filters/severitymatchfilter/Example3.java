/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ParameterName">
      <property name="severity" value="info"/>
    </module>
    <module name="MethodName"/>
  </module>
  <module name="SeverityMatchFilter">
    <property name="acceptOnMatch" value="false"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.severitymatchfilter;

// xdoc section - start
public class Example3 {
  public void method1(int V1){} // violation, ParameterName's severity is info

  // filtered violation below 'must match pattern'
  public void Method2(){}
}
// xdoc section - end
