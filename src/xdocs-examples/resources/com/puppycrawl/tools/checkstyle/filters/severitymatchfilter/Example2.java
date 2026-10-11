/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ParameterName">
      <property name="severity" value="info"/>
    </module>
    <module name="MethodName"/>
    <module
        name="com.puppycrawl.tools.checkstyle.checks.design.VisibilityModifierCheck">
      <property name="severity" value="warning"/>
    </module>
  </module>
  <module name="SeverityMatchFilter">
    <property name="severity" value="info"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.severitymatchfilter;

// xdoc section - start
public class Example2 {
  // filtered violation below 'must be private'
  int field1;

  // violation below 'must match pattern'
  public void method1(int V1){}

  // filtered violation below 'must match pattern'
  public void Method2(){}
}
// xdoc section - end
